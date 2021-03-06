/*
 * Copyright (c) 2002-2017 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.causalclustering.load_balancing.procedure;

import org.junit.Test;

import java.util.List;

import org.neo4j.causalclustering.load_balancing.Endpoint;
import org.neo4j.causalclustering.load_balancing.LoadBalancingResult;
import org.neo4j.collection.RawIterator;
import org.neo4j.helpers.AdvertisedSocketAddress;
import org.neo4j.kernel.api.exceptions.ProcedureException;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResultFormatV1Test
{
    @Test
    public void shouldSerializeToAndFromRecordFormat() throws Exception
    {
        // given
        List<Endpoint> writers = asList(
                Endpoint.write( new AdvertisedSocketAddress( "write", 1 ) ),
                Endpoint.write( new AdvertisedSocketAddress( "write", 2 ) ),
                Endpoint.write( new AdvertisedSocketAddress( "write", 3 ) ) );
        List<Endpoint> readers = asList(
                Endpoint.read( new AdvertisedSocketAddress( "read", 4 ) ),
                Endpoint.read( new AdvertisedSocketAddress( "read", 5 ) ),
                Endpoint.read( new AdvertisedSocketAddress( "read", 6 ) ),
                Endpoint.read( new AdvertisedSocketAddress( "read", 7 ) ) );
        List<Endpoint> routers = singletonList(
                Endpoint.route( new AdvertisedSocketAddress( "route", 8 ) )
        );

        long ttlSeconds = 5;
        LoadBalancingResult original = new LoadBalancingResult( routers, writers, readers, ttlSeconds * 1000 );

        // when
        RawIterator<Object[],ProcedureException> records = ResultFormatV1.build( original );

        // then
        assertTrue( records.hasNext() );
        LoadBalancingResult parsed = ResultFormatV1.parse( records );

        assertFalse( records.hasNext() );
        assertEquals( original, parsed );
    }
}
