// Copyright (C) Continental AG
// All Rights Reserved
// COMPONENT: env_mock

#ifndef ENV_MOCK_H_
#define ENV_MOCK_H_

#include <gmock/gmock.h>
#include <multiply/multiply.h>

class Mock_Env
{
public:
	/**************************************************************************************************/
	/* BAI                                                                                            */
	/**************************************************************************************************/
	MOCK_METHOD0(BAI_s_GetResetStatus, void(void));


};

extern Mock_Env* obj_Mock_Env_Ptr;

#endif

