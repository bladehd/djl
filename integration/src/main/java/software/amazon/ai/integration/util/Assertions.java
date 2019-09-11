/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package software.amazon.ai.integration.util;

import software.amazon.ai.integration.exceptions.FailedTestException;
import software.amazon.ai.ndarray.NDArray;
import software.amazon.ai.ndarray.NDArrays;
import software.amazon.ai.ndarray.NDList;
import software.amazon.ai.nn.Parameter;

public final class Assertions {

    private Assertions() {}

    public static void assertTrue(boolean statement, String errorMessage)
            throws FailedTestException {
        if (!statement) {
            throw new FailedTestException(errorMessage);
        }
    }

    public static void assertTrue(boolean statement) throws FailedTestException {
        assertTrue(statement, "Statement is not True!");
    }

    public static void assertFalse(boolean statement, String errorMessage)
            throws FailedTestException {
        if (statement) {
            throw new FailedTestException(errorMessage);
        }
    }

    public static void assertFalse(boolean statement) throws FailedTestException {
        assertFalse(statement, "Statement is not False!");
    }

    public static void assertEquals(NDList expected, NDList actual, String errorMessage)
            throws FailedTestException {
        assertEquals(expected.size(), actual.size(), "The NDLists have different sizes");
        int size = expected.size();
        for (int i = 0; i < size; i++) {
            assertEquals(expected.get(i), actual.get(i), "The NDLists differ on element " + i);
        }
    }

    public static void assertEquals(NDList expected, NDList actual) throws FailedTestException {
        assertEquals(expected, actual, "Two NDArrays are different!");
    }

    public static void assertEquals(NDArray expected, NDArray actual, String errorMessage)
            throws FailedTestException {
        if (!NDArrays.equals(expected, actual)) {
            throw new FailedTestException(
                    errorMessage + "\nExpected: " + expected + "\n Actual: " + actual);
        }
    }

    public static void assertEquals(NDArray expected, NDArray actual) throws FailedTestException {
        assertEquals(expected, actual, "Two NDArrays are different!");
    }

    public static void assertEquals(float expected, float actual, String errorMessage)
            throws FailedTestException {
        if (expected != actual) {
            throw new FailedTestException(errorMessage);
        }
    }

    public static void assertEquals(Parameter expected, Parameter actual)
            throws FailedTestException {
        if (!expected.equals(actual)) {
            throw new FailedTestException("Two Parameters are different!");
        }
    }

    public static void assertAlmostEquals(NDList expected, NDList actual, double rtol, double atol)
            throws FailedTestException {
        assertEquals(expected.size(), actual.size(), "The NDLists have different sizes");
        int size = expected.size();
        for (int i = 0; i < size; i++) {
            assertAlmostEquals(expected.get(i), actual.get(i), rtol, atol);
        }
    }

    public static void assertAlmostEquals(NDList expected, NDList actual)
            throws FailedTestException {
        assertAlmostEquals(expected, actual, 1e-5, 1e-3);
    }

    public static void assertAlmostEquals(
            NDArray expected, NDArray actual, double rtol, double atol) throws FailedTestException {
        Number[] expectedDoubleArray = expected.toArray();
        Number[] actualDoubleArray = actual.toArray();
        if (expectedDoubleArray.length != actualDoubleArray.length) {
            throw new FailedTestException("The length of two NDArray are different!");
        }
        for (int i = 0; i < expectedDoubleArray.length; i++) {
            double a = expectedDoubleArray[i].doubleValue();
            double b = actualDoubleArray[i].doubleValue();
            if (Math.abs(a - b) > (atol + rtol * Math.abs(b))) {
                throw new FailedTestException("expect = " + a + ", actual =" + b);
            }
        }
    }

    public static void assertAlmostEquals(NDArray expected, NDArray actual)
            throws FailedTestException {
        assertAlmostEquals(expected, actual, 1e-5, 1e-3);
    }

    public static void assertNonZeroNumber(NDArray array, int number, String errorMessage)
            throws FailedTestException {
        if (array.nonzero() != number) {
            throw new FailedTestException(errorMessage);
        }
    }

    public static void assertNonZeroNumber(NDArray array, int number) throws FailedTestException {
        assertNonZeroNumber(array, number, "Assertion failed!");
    }

    public static void assertInPlace(NDArray expected, NDArray actual, String errorMessage)
            throws FailedTestException {
        if (expected != actual) {
            throw new FailedTestException(errorMessage);
        }
    }

    public static void assertInPlace(NDArray expected, NDArray actual) throws FailedTestException {
        assertInPlace(expected, actual, "Assertion failed!");
    }

    @SuppressWarnings({"PMD.PreserveStackTrace", "PMD.DoNotUseThreads"})
    public static void assertThrows(Runnable function, Class<?> exceptionClass, String errorMessage)
            throws FailedTestException {
        try {
            function.run();
        } catch (Exception e) {
            if (exceptionClass.isInstance(e)) {
                return;
            } else {
                throw new FailedTestException(errorMessage + " - wrong exception type thrown");
            }
        }
        throw new FailedTestException(errorMessage + " - did not throw an exception");
    }

    @SuppressWarnings("PMD.DoNotUseThreads")
    public static void assertThrows(Runnable function, Class<?> exceptionClass)
            throws FailedTestException {
        assertThrows(function, exceptionClass, "Assertion failed!");
    }
}
