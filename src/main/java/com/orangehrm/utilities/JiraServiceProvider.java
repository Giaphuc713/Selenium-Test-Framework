package com.orangehrm.utilities;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.FluentCreate;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class JiraServiceProvider {
    private JiraClient Jira;

    private String project;

    private String JiraUrl;

    public JiraServiceProvider(String JiraUrl, String username, String password, String project) {
        this.JiraUrl = JiraUrl;

        BasicCredentials creds = new BasicCredentials(username, password);

        // initialize the Jira client with the url and the credentials

        try {
            Jira = new JiraClient(JiraUrl, creds);
        } catch (JiraException e) {
            throw new RuntimeException("Failed to initialize Jira client: " + e.getMessage(), e);
        }

        this.project = project;

    }

    public void createJiraIssue(String issueType, String summary, String description, String reporterName) {
        try {
            // avoid creating duplicate issue
            // NOTE: Jira Cloud removed /rest/api/2/search (410 Gone).
            // The duplicate check is best-effort; if it fails we still attempt to create
            // the issue.
            try {
                Issue.SearchResult result = Jira.searchIssues("summary ~ \"" + summary + "\"");
                if (result.issues != null && result.issues.size() > 0) {
                    System.out.println("Issue with summary " + summary + " already exists. Skipping creation.");
                    return;
                }
            } catch (Exception searchEx) {
                System.out
                        .println("[WARN] Jira duplicate-check failed (API may be deprecated): " + searchEx.getMessage()
                                + " — proceeding to create issue.");
            }

            // Create issue if not exist
            // NOTE: Jira Cloud auto-assigns the API token owner as reporter.
            // Setting Field.REPORTER manually causes a 400 error for non-admin tokens.
            FluentCreate fluentCreate = Jira.createIssue(project, issueType);
            fluentCreate.field(Field.SUMMARY, summary);
            fluentCreate.field(Field.DESCRIPTION, description);
            Issue newissue = fluentCreate.execute();
            System.out.println("Issue created with ID: " + newissue.getId());

        } catch (JiraException e) {
            e.printStackTrace();
        }
    }

}
