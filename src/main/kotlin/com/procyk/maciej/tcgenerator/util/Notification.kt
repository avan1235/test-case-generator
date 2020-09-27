package com.procyk.maciej.tcgenerator.util

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

private const val GROUP_DISPLAY_ID = "Test Case Generator"

fun showNotification(
    message: String,
    title: String = "Test Case Generator",
    type: NotificationType = NotificationType.INFORMATION
) {
    Notification(GROUP_DISPLAY_ID, title, message, type).let(Notifications.Bus::notify)
}
