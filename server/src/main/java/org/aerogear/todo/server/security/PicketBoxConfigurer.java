/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.aerogear.todo.server.security;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
 
import org.picketbox.core.config.ConfigurationBuilder;
import org.picketbox.core.identity.jpa.EntityManagerLookupStrategy;
import org.picketlink.extensions.core.social.fb.FacebookAuthenticationMechanism;
import org.picketlink.extensions.core.social.openid.OpenIDAuthenticationMechanism;
import org.picketlink.extensions.core.social.twitter.TwitterAuthenticationMechanism;

/**
 * <p>
 * Bean responsible for producing the {@link ConfigurationBuilder}.
 * </p>
 * 
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 * 
 */
public class PicketBoxConfigurer {

    /**
     * <p>
     * Injects a {@link JPATemplate} that should be used to execute IDM operations. PicketLink Extensions provides a default
     * implementation called {@link DefaultJPATemplate}.
     * </p>
     */
    @Inject
    private EntityManagerLookupStrategy entityManagerLookupStrategy;

    @Inject
    private FacebookAuthenticationMechanism fbAuthenticationMechanism;

    @Inject
    private OpenIDAuthenticationMechanism openidAuthenticationMechanism;

    @Inject
    private TwitterAuthenticationMechanism twitterAuthenticationMechanism;

    /**
     * <p>
     * Produces the {@link ConfigurationBuilder}.
     * </p>
     * 
     * @return
     */
    @Produces
    public ConfigurationBuilder produceConfiguration() {
        ConfigurationBuilder builder = new ConfigurationBuilder(); 
        
        // configure the social authentication mechanisms
        builder
            .authentication()
                .mechanism(this.fbAuthenticationMechanism)
                .mechanism(this.openidAuthenticationMechanism)
                .mechanism(this.twitterAuthenticationMechanism);
        
        // configure the identity manager using a JPA-based identity store.
        builder
            .identityManager()
                .jpaStore().entityManagerLookupStrategy(this.entityManagerLookupStrategy);
               
        // session management configuration
        builder
            .sessionManager()
                .inMemorySessionStore();

        return builder;
    }

}