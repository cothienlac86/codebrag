package com.softwaremill.codebrag.usecase

import org.scalatest.{BeforeAndAfterEach, FlatSpec}
import org.scalatest.mock.MockitoSugar
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import com.softwaremill.codebrag.service.data.UserJson
import com.softwaremill.codebrag.domain.builder.UserAssembler
import com.softwaremill.codebrag.dao.UserDAO
import com.softwaremill.codebrag.domain.UserSettings
import org.mockito.Matchers

class IncomingSettingsSpec extends FlatSpec with MockitoSugar with ShouldMatchers with BeforeAndAfterEach {

  val existingSettings = UserSettings(
    avatarUrl =  "http://codebrag.com/avatar",
    emailNotificationsEnabled = true,
    appTourDone = false
  )

  it should "update only incoming settings" in {
    // given
    val noNotificationsSettings = newSettingsWithNotificationsDisabled
    val tourDoneSettings = newSettingsWithAppTourDone

    // when
    val updatedNotifications = noNotificationsSettings.applyTo(existingSettings)
    val updatedWelcomeFollowup = tourDoneSettings.applyTo(existingSettings)

    // then
    updatedNotifications should equal(existingSettings.copy(emailNotificationsEnabled = false))
    updatedWelcomeFollowup should equal(existingSettings.copy(appTourDone = true))
  }

  it should "update nothing when no values in incoming settings found" in {
    // given
    val emptySettings = IncomingSettings(None, None)

    // when
    val updatedSettings = emptySettings.applyTo(existingSettings)

    // then
    updatedSettings should equal(existingSettings)
  }

  val newSettingsWithNotificationsDisabled = IncomingSettings(emailNotificationsEnabled = Some(false), appTourDone = None)
  val newSettingsWithAppTourDone = IncomingSettings(None, appTourDone = Some(true))
}