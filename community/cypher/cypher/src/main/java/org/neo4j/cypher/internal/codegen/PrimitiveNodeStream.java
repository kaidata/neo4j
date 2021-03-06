/*
 * Copyright (c) 2002-2017 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.codegen;

import java.util.Iterator;
import java.util.stream.LongStream;

public class PrimitiveNodeStream extends PrimitiveEntityStream<NodeIdWrapper>
{
    public PrimitiveNodeStream( LongStream inner )
    {
        super( inner );
    }

    public static PrimitiveNodeStream of( long[] array )
    {
        return new PrimitiveNodeStream( LongStream.of( array ) );
    }

    @Override
    // This method is only used when we do not know the element type at compile time, so it has to box the elements
    public Iterator<NodeIdWrapper> iterator()
    {
        return inner.mapToObj( NodeIdWrapper::new ).iterator();
    }
}
