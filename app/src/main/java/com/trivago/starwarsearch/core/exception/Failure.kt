package com.trivago.starwarsearch.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
open class Failure {

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}
