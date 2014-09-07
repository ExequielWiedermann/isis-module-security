/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.security.dom.permission;

import java.util.Collection;
import org.isisaddons.module.security.dom.feature.ApplicationFeatureId;

public abstract class PermissionsEvaluationServiceAbstract implements PermissionsEvaluationService {


    @Override
    public ApplicationPermissionValueSet.Evaluation evaluate(
            final ApplicationFeatureId featureId,
            final ApplicationPermissionMode mode,
            final Collection<ApplicationPermissionValue> permissionValues) {

        final Iterable<ApplicationPermissionValue> ordered = ordered(permissionValues);

        for (ApplicationPermissionValue permissionValue : ordered) {
            if(permissionValue.implies(featureId, mode)) {
                return new ApplicationPermissionValueSet.Evaluation(permissionValue, true);
            } else if(permissionValue.refutes(featureId, mode)) {
                return new ApplicationPermissionValueSet.Evaluation(permissionValue, false);
            }
        }
        return null;
    }

    protected abstract Iterable<ApplicationPermissionValue> ordered(Collection<ApplicationPermissionValue> permissionValues);

}