package com.procyk.maciej.tcgenerator.providers.jira

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName

val JIRA_CREDENTIAL_ATTRIBUTES = CredentialAttributes(generateServiceName("TestCaseGenerator", "JiraProvider"))
