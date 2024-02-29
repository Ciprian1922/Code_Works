#include <multiply/multiply.h>
#include <gtest/gtest.h>
#include <mock.h>
#include <gtest_common.h>

#define TEST_MULTIPLY_01  TEST_F
#define TEST_MULTIPLY2_01 TEST_F
#define TEST_MULTIPLY2_02 TEST_F
#define TEST_MULTIPLY3_01 TEST_F
#define TEST_MULTIPLY3_02 TEST_F
#define TEST_MULTIPLY2_03 TEST_F


Mock_Env*       obj_Mock_Env_Ptr = NULL;


// Test suites are used to group unit tests that use the same initialization work
// before the test is run.
class TestSuite_SelfTest : public ::testing::Test
{
protected:
	TestSuite_SelfTest()
	{ // initialization code here
		obj_Mock_Env_Ptr = new Mock_Env;

		/* Global data */
		//initialise_global_data();

		/* Set expected values for global data checks */
		//initialise_expected_global_data();
	}
	~TestSuite_SelfTest()
	{ // cleanup any pending stuff, but no exceptions allowed
		delete obj_Mock_Env_Ptr;
	}
	void SetUp()
	{ // code here will execute just before the test ensues
	}
	void TearDown()
	{ // code here will be called just after the test completes
	  // ok to through exceptions from here if need be
	}
};

/// \test Testing function multiply.
///
/// \description
///
/// \pre
///  None\n
///
/// \post
///     The global variables change as follows: \n
///
/// \traceability
TEST_MULTIPLY_01(TestSuite_SelfTest, TestMultiply_01)
{
	/* Test case data declaration */
	int a = 1;
	int b = 1;
    int expected = 1;

	/* Expect call sequence */
	{
		InSequence s;
	}

	/* Call SUT */
	int actual = multiply(a, b);

	/* Test case check */
    ASSERT_EQ(expected, actual);

}


/// \test Testing function multiply.
///
/// \description
///
/// \pre
///  None\n
///
/// \post
///     The global variables change as follows: \n
///
/// \traceability
TEST_MULTIPLY2_01(TestSuite_SelfTest, TestMultiply2_01)
{
	/* Test case data declaration */
	int a = 0;
	int b = 0;
	int expected = E_OK;

	/* Expect call sequence */
	{
	   InSequence s;
	   EXPECT_CALL(*obj_Mock_Env_Ptr, BAI_s_GetResetStatus()).Times(1);
	}

	/* Call SUT */
	int actual = multiply2(a, b);

	/* Test case check */
    ASSERT_EQ(expected, actual);
}

/// \test Testing function multiply.
///
/// \description
///
/// \pre
///  None\n
///
/// \post
///     The global variables change as follows: \n
///
/// \traceability
TEST_MULTIPLY2_02(TestSuite_SelfTest, TestMultiply2_02)
{
	/* Test case data declaration */
	int a = 3;
	int b = 1;
	int expected = 4;

	/* Expect call sequence */
	{
		InSequence s;
		EXPECT_CALL(*obj_Mock_Env_Ptr, BAI_s_GetResetStatus()).Times(1);
	}

	/* Call SUT */
	int actual = multiply2(a, b);

	/* Test case check */
	ASSERT_EQ(expected, actual);
}

/// \test Testing function multiply2.
///
/// \description
///
/// \pre
///  None\n
///
/// \post
///     The global variables change as follows: \n
///
/// \traceability
TEST_MULTIPLY2_03(TestSuite_SelfTest, TestMultiply2_03)
{
	/* Test case data declaration*/
	int a = 3;
	int b = 1;
	int expected = 4;

	/* Expect call sequence */
	{
		InSequence s;
		EXPECT_CALL(*obj_Mock_Env_Ptr, BAI_s_GetResetStatus()).Times(1);
	}

	/* Call SUT */
	int actual = multiply2(a, b);

	/* Test case check */
	ASSERT_EQ(expected, actual);
}

/// \test Testing function multiply3.
///
/// \description
///
/// \pre
///  None\n
///
/// \post
///     The global variables change as follows: \n
///
/// \traceability
TEST_MULTIPLY3_01(TestSuite_SelfTest, Testmultiply3_01)
{
	/* Test case data declaration*/
	int a = 0;
	int b = 1;
	int expected = 0;

	/* Expect call sequence */
	{
		InSequence s;
	}

	/* Call SUT */
    int actual = multiply3(a, b);

	/* Test case check */
    ASSERT_EQ(actual, expected);
}

/// \test Testing function multiply3.
///
/// \description
///
/// \pre
///  None\n
///
/// \post
///     The global variables change as follows: \n
///
/// \traceability
TEST_MULTIPLY3_02(TestSuite_SelfTest, Testmultiply3_02)
{
	/* Test case data declaration*/
	int a = 2;
	int b = 1;
	int expected = 1;

	/* Expect call sequence */
	{
		InSequence s;
	}

	/* Call SUT */
	int actual = multiply3(a, b);

	/* Test case check */
	ASSERT_EQ(actual, expected);
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}