/*
 * Copyright 2020 HM Revenue & Customs
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

package v1.models.request

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class NinoApplication(
                            nino: String,
                            gender: Gender,
                            entryDate: DateModel,
                            birthDate: DateModel,
                            birthDateVerification: Option[BirthDateVerification],
                            officeNumber: String,
                            contactNumber: Option[String],
                            applicantNames: Seq[NameModel],
                            applicantHistoricNames: Option[Seq[NameModel]],
                            applicantAddresses: Seq[AddressModel],
                            applicantHistoricAddresses: Option[Seq[AddressModel]],
                            applicantMarriages: Option[Seq[Marriage]],
                            applicantOrigin: Option[OriginData],
                            applicantPriorResidency: Option[Seq[PriorResidencyModel]],
                            abroadLiability: Option[AbroadLiabilityModel],
                            nationalityCode: Option[String]
                          )

object NinoApplication {
  private val ninoRegex = "^((?!(BG|GB|KN|NK|NT|TN|ZZ)|(D|F|I|Q|U|V)[A-Z]|[A-Z](D|F|I|O|Q|U|V))[A-Z]{2})[0-9]{6}[A-D]$"
  private val officeNumberRegex = "^([0-9]{1,4})$"
  private val contactNumberRegex = "^([+]{0,1}[0-9 ]{1,70}[0-9])$"
  private val nationalityCodeRegex = "^[A-Z]{3}$"

  private[models] def validateAgainstRegex(value: String, regex: String): Boolean = {
    value.matches(regex)
  }

  private[models] def validateCountry(input: Int): Boolean = {
    input >= 0 && input <= 286
  }

  implicit val writes: Writes[NinoApplication] = Json.writes[NinoApplication]

  private val ninoPath = __ \ "nino"
  private val genderPath = __ \ "gender"
  private val entryDatePath = __ \ "entryDate"
  private val birthDatePath = __ \ "birthDate"
  private val birthDateVerificationPath = __ \ "birthDateVerification"
  private val officeNumberPath = __ \ "officeNumber"
  private val contactNumberPath = __ \ "contactNumber"
  private val namesPath = __ \ "name"
  private val historicalNamesPath = __ \ "historicNames"
  private val addressesPath = __ \ "address"
  private val historicalAddressesPath = __ \ "historicAddresses"
  private val applicantMarriagesPath = __ \ "marriages"
  private val originDataPath = __ \ "originData"
  private val priorResidencyPath = __ \ "priorResidency"
  private val abroadLiabilityPath = __ \ "abroadLiability"
  private val nationalityCodePath = __ \ "nationalityCode"

  private def commonError(fieldName: String) = {
    JsonValidationError(s"There has been an error parsing the $fieldName field. Please check against the regex.")
  }

  implicit val reads: Reads[NinoApplication] = (
    ninoPath.read[String].filter(commonError("nino"))(validateAgainstRegex(_, ninoRegex)) and
      genderPath.read[Gender] and
      entryDatePath.read[DateModel] and
      birthDatePath.read[DateModel] and
      birthDateVerificationPath.readNullable[BirthDateVerification] and
      officeNumberPath.read[String].filter(commonError("office number"))(validateAgainstRegex(_, officeNumberRegex)) and
      contactNumberPath.readNullable[String].filter(commonError("contact number"))(_.fold(true)(validateAgainstRegex(_, contactNumberRegex))) and
      namesPath.read[NameModel].map(Seq(_)) and
      historicalNamesPath.readNullable[Seq[NameModel]] and
      addressesPath.read[AddressModel].map(Seq(_)) and
      historicalAddressesPath.readNullable[Seq[AddressModel]] and
      applicantMarriagesPath.readNullable[Seq[Marriage]] and
      originDataPath.readNullable[OriginData] and
      priorResidencyPath.readNullable[Seq[PriorResidencyModel]] and
      abroadLiabilityPath.readNullable[AbroadLiabilityModel] and
      nationalityCodePath.readNullable[String].filter(
        commonError("nationality code"))(nationalityCode => nationalityCode.fold(true)(validateAgainstRegex(_, nationalityCodeRegex)))
  ) (NinoApplication.apply _)
}
