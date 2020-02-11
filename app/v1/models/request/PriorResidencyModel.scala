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
import play.api.libs.json.{Json, Reads, _}

case class PriorResidencyModel(
                                startDate: Option[DateModel] = None,
                                endDate: Option[DateModel] = None
                              )

object PriorResidencyModel {

  implicit val reads: Reads[PriorResidencyModel] = (
    (__ \ "priorStartDate").readNullable[DateModel] and
    (__ \ "priorEndDate").readNullable[DateModel]
  )(PriorResidencyModel.apply _)

  implicit val writes: Writes[PriorResidencyModel] = Json.writes[PriorResidencyModel]
}
