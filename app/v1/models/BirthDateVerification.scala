/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v1.models

import play.api.libs.json.{JsString, Json, Reads, Writes, __}

sealed trait BirthDateVerification{
  val value: String
}

object BirthDateVerification {
  private def valueCheck(input: String): BirthDateVerification = {
    input match {
      case Unverified.value => Unverified
      case Verified.value => Verified
      case VerificationNotKnown.value => VerificationNotKnown
      case CoegConfirmed.value => CoegConfirmed
      case _ => throw new IllegalArgumentException(s"Unable to parse birthDateVerification field. Available values are: " +
        s"UNVERIFIED, VERIFIED, NOT KNOWN, COEG CONFIRMED")
    }
  }

  implicit val reads: Reads[BirthDateVerification] = __.read[String] map valueCheck

  implicit val writes: Writes[BirthDateVerification] = Writes[BirthDateVerification] ( verificationValue => JsString(verificationValue.value) )
}

case object Unverified extends BirthDateVerification {
  val value = "UNVERIFIED"
}

case object Verified extends BirthDateVerification {
  val value = "VERIFIED"
}

case object VerificationNotKnown extends BirthDateVerification {
  val value = "NOT KNOWN"
}

case object CoegConfirmed extends BirthDateVerification {
  val value = "COEG CONFIRMED"
}
