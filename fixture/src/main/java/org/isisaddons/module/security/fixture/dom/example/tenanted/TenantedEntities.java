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
package org.isisaddons.module.security.fixture.dom.example.tenanted;

import java.util.List;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.fixture.dom.example.nontenanted.NonTenantedEntity;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TenantedEntity.class
)
@DomainServiceLayout(menuOrder = "20")
public class TenantedEntities {

    //region > identification in the UI
    // //////////////////////////////////////

    @Programmatic
    public String getId() {
        return "TenantedEntities";
    }

    public String iconName() {
        return "TenantedEntity";
    }

    //endregion

    //region > listAll (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<TenantedEntity> listAll() {
        return container.allInstances(TenantedEntity.class);
    }

    //endregion

    //region > create (action)
    // //////////////////////////////////////

    @MemberOrder(sequence = "2")
    public TenantedEntity create(
            @Parameter(maxLength = NonTenantedEntity.MAX_LENGTH_NAME)
            @ParameterLayout(named="Name")
            final String name,
            final ApplicationTenancy tenancy) {
        final TenantedEntity obj = container.newTransientInstance(TenantedEntity.class);
        obj.setName(name);
        obj.setApplicationTenancy(tenancy);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services
    // //////////////////////////////////////

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
