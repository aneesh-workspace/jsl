#!/usr/bin/env groovy

import org.apache.commons.lang.StringUtils

def call(String filter_string, int occurence) {
    echo(" Inside JSL filterLogs")
    def logs = CurrentBuild.rawBuild.getLog(10000).join('\n')
    int count = StringUtils.countMatches(logs, filter_string);
    if (count > occurence -1) {
        currentBuild.result='UNSTABLE'
}
}
