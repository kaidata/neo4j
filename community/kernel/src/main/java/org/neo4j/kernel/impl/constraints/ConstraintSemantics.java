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
package org.neo4j.kernel.impl.constraints;

import java.util.Iterator;

import org.neo4j.cursor.Cursor;
import org.neo4j.kernel.api.schema.NodePropertyDescriptor;
import org.neo4j.kernel.api.schema.RelationshipPropertyDescriptor;
import org.neo4j.kernel.api.constraints.PropertyConstraint;
import org.neo4j.kernel.api.exceptions.schema.CreateConstraintFailureException;
import org.neo4j.kernel.impl.store.record.ConstraintRule;
import org.neo4j.storageengine.api.NodeItem;
import org.neo4j.storageengine.api.RelationshipItem;
import org.neo4j.storageengine.api.StoreReadLayer;
import org.neo4j.storageengine.api.txstate.ReadableTransactionState;
import org.neo4j.storageengine.api.txstate.TxStateVisitor;

/**
 * Implements semantics of constraint creation and enforcement.
 */
public interface ConstraintSemantics
{
    void validateNodePropertyExistenceConstraint( Iterator<Cursor<NodeItem>> allNodes, NodePropertyDescriptor descriptor )
            throws CreateConstraintFailureException;

    void validateRelationshipPropertyExistenceConstraint( Cursor<RelationshipItem> allRels,
            RelationshipPropertyDescriptor descriptor ) throws CreateConstraintFailureException;

    PropertyConstraint readConstraint( ConstraintRule rule );

    ConstraintRule writeUniquePropertyConstraint( long ruleId, NodePropertyDescriptor descriptor, long indexId );

    ConstraintRule writeNodePropertyExistenceConstraint( long ruleId, NodePropertyDescriptor descriptor )
            throws CreateConstraintFailureException;

    ConstraintRule writeRelationshipPropertyExistenceConstraint( long ruleId, RelationshipPropertyDescriptor descriptor )
            throws CreateConstraintFailureException;

    TxStateVisitor decorateTxStateVisitor( StoreReadLayer storeLayer, ReadableTransactionState state,
            TxStateVisitor visitor );
}
