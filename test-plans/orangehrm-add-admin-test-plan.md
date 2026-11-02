# OrangeHRM — Test Plan: Add a New Admin

## Scope
Verify adding a system user with the **Admin** role from **Admin → User Management → Users → Add**. The public OrangeHRM demo was used for reconnaissance only; no user was saved.

## Observed application flow
- Login route: `https://opensource-demo.orangehrmlive.com/web/index.php/auth/login`
- After login, the dashboard sidebar is expanded by default and contains **Admin**.
- Selecting **Admin** opens the System Users page; **Add** opens the **Add User** form at `/web/index.php/admin/saveSystemUser`.
- The form shows **User Role**, **Employee Name** (autocomplete), **Status**, **Username**, **Password**, **Confirm Password**, and **Save**.

## Preconditions and test data
- Use an authorized demo/test account with Admin privileges. Obtain credentials from the configured secret source; do not hardcode them in test source or reports.
- Ensure a valid, active employee is available for Employee Name autocomplete.
- Generate a unique username for each run (for example, `qa_admin_<timestamp>`); avoid reusing accounts in the shared demo.
- Use a password satisfying the application's current password policy and matching confirmation.
- Run creation tests only in an authorized test environment. Remove test accounts afterward when permitted.

## Test cases

### TC-ADD-ADMIN-001 — Create an enabled Admin account
**Priority:** High | **Type:** Positive / end-to-end

1. Navigate to the login route and sign in with the authorized Admin account.
2. If the left navigation is collapsed, expand it; verify **Admin** is visible.
3. Select **Admin** in the sidebar.
4. On System Users, select **Add**.
5. On the Add User form, select **Admin** for User Role.
6. Enter a valid employee name and select the matching suggestion from autocomplete.
7. Select **Enabled** for Status.
8. Enter a unique username, a policy-compliant password, and the same value in Confirm Password.
9. Select **Save**.
10. Verify the app returns to the System Users list and shows a success confirmation.
11. Search for the new username and verify the row shows the username, **Admin** role, linked employee, and **Enabled** status.
12. If allowed by the environment, log out and verify the new credentials can sign in; verify the Admin navigation is available. Log out afterward.

**Expected result:** The account is created once with the selected employee, Admin role, Enabled status, and unique username. The new user appears in the list and (when tested) can sign in with Admin access.

### TC-ADD-ADMIN-002 — Required fields are validated
**Priority:** High | **Type:** Negative / validation

1. Sign in, navigate to **Admin → Add** as in TC-ADD-ADMIN-001.
2. Leave required fields empty and select **Save**.
3. Verify the form stays open, required-field validation is shown for the missing inputs, and no account is created.
4. Repeat with each required field omitted individually where practical: User Role, Employee Name, Status, Username, Password, and Confirm Password.

**Expected result:** Missing required values are clearly identified; no account is created and the user remains on the form.

### TC-ADD-ADMIN-003 — Duplicate username is rejected
**Priority:** High | **Type:** Negative / business rule

1. Open **Admin → Add**.
2. Enter valid role, employee, status, and password values, but use an already-existing username.
3. Select **Save**.
4. Verify a duplicate-username validation message is displayed and no second account is created.

**Expected result:** The duplicate username is rejected; the existing account remains unchanged and no duplicate row is added.

### TC-ADD-ADMIN-004 — Password confirmation must match
**Priority:** Medium | **Type:** Negative / validation

1. Open **Admin → Add** and complete all other fields with valid values and a unique username.
2. Enter different values in Password and Confirm Password.
3. Select **Save**.
4. Verify a mismatch validation message is displayed and no account is created.

**Expected result:** The form rejects mismatched passwords and remains available for correction.

## Automation notes
- Prefer accessible labels/text locators for the sidebar, buttons, form controls, and autocomplete suggestion; avoid positional selectors where labels are available.
- Wait for navigation, autocomplete suggestions, and save feedback explicitly rather than using fixed sleeps.
- Keep passwords and login credentials out of logs, screenshots, and committed test data.
- Make account creation idempotent by generating a unique username; do not assume public demo data is stable.
