/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.common.schema.model.constraint.property;

import net.jcip.annotations.Immutable;
import eu.esdihumboldt.hale.common.schema.model.Constraint;
import eu.esdihumboldt.hale.common.schema.model.GroupPropertyConstraint;
import eu.esdihumboldt.hale.common.schema.model.PropertyConstraint;

/**
 * Specifies the cardinality for a property, default is for a property to occur
 * exactly once.
 * 
 * @author Simon Templer
 */
@Immutable
@Constraint(mutable = false)
public class Cardinality implements GroupPropertyConstraint, PropertyConstraint {

	/**
	 * Value for unrestricted {@link #maxOccurs}
	 */
	public static final long UNBOUNDED = -1;

	/**
	 * Cardinality constraint for properties that occur exactly once (one)
	 */
	public static final Cardinality CC_EXACTLY_ONCE = new Cardinality(1, 1);

	/**
	 * Cardinality constraint for properties that occur once or not at all (zero
	 * to one)
	 */
	public static final Cardinality CC_OPTIONAL = new Cardinality(0, 1);

	/**
	 * Cardinality constraint for properties that occur at least once (one to
	 * infinity)
	 */
	public static final Cardinality CC_AT_LEAST_ONCE = new Cardinality(1, UNBOUNDED);

	/**
	 * Cardinality constraint for properties that occur in any number (zero to
	 * infinity)
	 */
	public static final Cardinality CC_ANY_NUMBER = new Cardinality(0, UNBOUNDED);

	/**
	 * Get the cardinality constraint with the given occurrences
	 * 
	 * @param minOccurs the number of minimum occurrences of a property, may not
	 *            be negative
	 * @param maxOccurs the number of maximum occurrences of a property,
	 *            {@value #UNBOUNDED} for an infinite maximum occurrence
	 * @return the cardinality constraint
	 */
	public static Cardinality get(long minOccurs, long maxOccurs) {
		// use singletons if possible
		if (minOccurs == 0) {
			if (maxOccurs == 1) {
				return CC_OPTIONAL;
			}
			else if (maxOccurs == UNBOUNDED) {
				return CC_ANY_NUMBER;
			}
		}
		else if (minOccurs == 1) {
			if (maxOccurs == 1) {
				return CC_EXACTLY_ONCE;
			}
			else if (maxOccurs == UNBOUNDED) {
				return CC_AT_LEAST_ONCE;
			}
		}

		return new Cardinality(minOccurs, maxOccurs);
	}

	/**
	 * The number of minimum occurrences of a property
	 */
	private final long minOccurs;

	/**
	 * The number of maximum occurrences of a property
	 */
	private final long maxOccurs;

	/**
	 * Creates a default cardinality constraint with {@link #minOccurs} and
	 * {@link #maxOccurs} one.<br>
	 * <br>
	 * NOTE: Instead of using the constructor to create new instances please use
	 * {@link #get(long, long)} if possible.
	 */
	public Cardinality() {
		this(1, 1);
	}

	/**
	 * Create a cardinality constraint.<br>
	 * <br>
	 * NOTE: Instead of using the constructor to create new instances please use
	 * {@link #get(long, long)} if possible.
	 * 
	 * @param minOccurs the number of minimum occurrences of a property, may not
	 *            be negative
	 * @param maxOccurs the number of maximum occurrences of a property,
	 *            {@value #UNBOUNDED} for an infinite maximum occurrence
	 */
	private Cardinality(long minOccurs, long maxOccurs) {
		super();
		this.minOccurs = minOccurs;
		this.maxOccurs = maxOccurs;
	}

	/**
	 * Get the number of minimum occurrences of a property
	 * 
	 * @return the number of minimum occurrences of a property
	 */
	public long getMinOccurs() {
		return minOccurs;
	}

	/**
	 * Get the number of maximum occurrences of a property
	 * 
	 * @return the number of maximum occurrences of a property
	 */
	public long getMaxOccurs() {
		return maxOccurs;
	}
}
