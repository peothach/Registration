package com.registration.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTypeTest {

  @Test
  void test_fromValue_ReturnUserTypeSILVER_IfDataIsSilver() {
    final UserType actual = UserType.fromValue("Silver");
    assertThat(actual).isEqualTo(UserType.SILVER);
  }

  @Test
  void test_fromValue_ReturnUserTypeGOLD_IfDataIsGold() {
    final UserType actual = UserType.fromValue("Gold");
    assertThat(actual).isEqualTo(UserType.GOLD);
  }

  @Test
  void test_fromValue_ReturnUserTypePLATINUM_IfDataIsPlatinum() {
    final UserType actual = UserType.fromValue("Platinum");
    assertThat(actual).isEqualTo(UserType.PLATINUM);
  }


}