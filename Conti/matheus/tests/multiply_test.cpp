#include <multiply/multiply.h>
#include <gtest/gtest.h>
#include <mock.h>

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


TEST_F(TestSuite_SelfTest, TestIntegerOne_One)
{
    const auto expected = 1;
    const auto actual = multiply(1, 1);
    ASSERT_EQ(expected, actual);
}

TEST_F(TestSuite_SelfTest, TestIntegerZero_Zero)
{
    const auto expected = 0;
    const auto actual = multiply2(0, 0);
    ASSERT_EQ(expected, actual);
}

TEST_F(TestSuite_SelfTest, TestIntegerZero_Zero_1)
{
	const auto expected = 0;
	const auto actual = multiply2(2, 0);
	ASSERT_EQ(expected, actual);
}

TEST_F(TestSuite_SelfTest, TestIntegerZero_One)
{
    const auto expected = 0;
    const auto actual = multiply3(0, 1);
    ASSERT_EQ(actual, expected);
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}