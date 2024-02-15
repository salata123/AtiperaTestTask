# Atipera test task

I had the opportunity to solve a test task for the Apitera company. The task was to use the GitHub API to create a list of a user's repositories and their branches.

## Acceptance criteria:

Acceptance criteria:

As an api consumer, given username and header "Accept: application/json", I would like to list all his github repositories, which are not forks. Information, which I require in the response, is:

Repository Name

Owner Login

For each branch it's name and last commit sha


As an api consumer, given not existing github user, I would like to receive 404 response in such a format:

{

    "status": ${responseCode}

    "message": ${whyHasItHappened}

}
## Set-up

All You need to do is:
- Create GitHub API token at: https://github.com/settings/tokens/new
- Create application.properties file in the project
- Run the project
- In the console, type any GitHub username to get their repositories
- Type 'exit' if You want to close the app

## Lessons Learned

This project allowed me to revisit my skills and enhance them. I learned a little about mocks, new libraries, a new test approach, and how to keep the code a bit cleaner.

## Thank You for checking out my project :)