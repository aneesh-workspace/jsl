package com.allianz.onemarketing.jenkins

class JenkinsUtils {
  static String getBuildTag(def steps) {
    return steps.env.BUILD_TAG
  }

  static void exitWithFailure(def steps, String message, Integer errorCode = 1) {
    steps.echo "ERROR: ${message}"
    steps.currentBuild.result = 'FAILURE'
    // Do not replace this shell call with 'checkOutput(["exit", errorCode.toString()])' since this causes a exception
    // ('Cannot run program "exit": error=2, No such file or directory').
    steps.sh "exit ${errorCode}"
  }

  static void markBuildAsUnstable(def steps, String message) {
    steps.echo "WARN: ${message}"
    steps.echo "Therefore marking build as unstable!"
    steps.currentBuild.result = 'UNSTABLE'
  }

  /**
   * Get a release reason built from the job environment
   *
   * @param steps
   * @return Release reason
   */
  static String getReleaseReason(def env) {
    return "Triggered by job ${env.JOB_NAME}, build ${env.BUILD_DISPLAY_NAME} on Jenkins ${env.TEAM}, node ${env.NODE_NAME}."
  }

  static String getGitBranch(def steps) {
    // The multibranch pipeline sets steps.env.BRANCH_NAME.
    // Our JobDSL sets steps.env.BRANCH_NAME when overwriting the Jenkinsfile, because using steps.scm fails with a pipeline error.
    return steps.env.BRANCH_NAME ?:
      // Pipeline jobs do not set the env.BRANCH_NAME, so we have to evaluate the currently checked out repository.
      steps.scm.branches[0].name
  }

  static String getGitBranchWithoutPrefix(def steps) {
    return getGitBranch(steps).minus("refs/heads/")
  }
}
