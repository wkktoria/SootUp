package sootup.java.frontend.minimaltestsuite.java7;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.java.frontend.minimaltestsuite.MinimalSourceTestSuiteBase;

/** @author: Hasitha Rajapakse * */
@Tag("Java8")
public class UnderscoreInIntTest extends MinimalSourceTestSuiteBase {
  @Override
  public MethodSignature getMethodSignature() {
    return identifierFactory.getMethodSignature(
        getDeclaredClassSignature(), "underscoreInInt", "void", Collections.emptyList());
  }

  /**
   *
   *
   * <pre>
   *     public void underscoreInInt(){
   * int a = 0b0111_1111_1111_1111_1111_1111_1111_1111;
   * }
   * </pre>
   */
  @Override
  public List<String> expectedBodyStmts() {
    return Stream.of("r0 := @this: UnderscoreInInt", "i0 = 2147483647", "return")
        .collect(Collectors.toList());
  }

  @Test
  public void test() {
    SootMethod method = loadMethod(getMethodSignature());
    assertJimpleStmts(method, expectedBodyStmts());
  }
}
