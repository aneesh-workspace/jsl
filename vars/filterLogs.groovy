#!/usr/bin/env groovy

import org.apache.commons.lang.StringUtils

def call(String filter_string, int occurence) {
    echo(" Inside JSL filterLogs")
    def logs = currentBuild.rawBuild.getLog(10000).join('\n')
    String zendesk = "zendesk";
    int count = StringUtils.countMatches(logs, filter_string);
    println("String ${filter_string} count is ${count}" )
    if (count > occurence -1) {
        currentBuild.result='UNSTABLE'
}
}
