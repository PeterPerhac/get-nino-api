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

package v1.config.featureSwitch

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import config.ConfigKeys.{useDesStubKey, logDesJsonKey, logDwpJsonKey}

@Singleton
class Features @Inject()(implicit config: Configuration) extends BaseFeature {

  lazy val useDesStub = new Feature(useDesStubKey)

  lazy val logDesJson = new Feature(logDesJsonKey)

  lazy val logDwpJson = new Feature(logDwpJsonKey)

}
