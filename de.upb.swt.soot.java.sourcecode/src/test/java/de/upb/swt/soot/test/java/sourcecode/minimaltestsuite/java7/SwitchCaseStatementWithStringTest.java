/**
 * @author: Markus Schmidt
 * @author: Hasitha Rajapakse
 */
package de.upb.swt.soot.test.java.sourcecode.minimaltestsuite.java7;

import categories.Java8Test;
import de.upb.swt.soot.core.model.SootMethod;
import de.upb.swt.soot.core.signatures.MethodSignature;
import de.upb.swt.soot.test.java.sourcecode.minimaltestsuite.MinimalSourceTestSuiteBase;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(Java8Test.class)
public class SwitchCaseStatementWithStringTest extends MinimalSourceTestSuiteBase {

  @Override
  public MethodSignature getMethodSignature() {
    return identifierFactory.getMethodSignature(
        "switchCaseStatementString", getDeclaredClassSignature(), "void", Collections.emptyList());
  }

  @Override
  public List<String> expectedBodyStmts() {
    // TODO: [ms] the generated jimple is not correct: $i1,$i1,$i3 are undefined/not assigned to
    // anything
    return Stream.of(
            "r0 := @this: SwitchCaseStatementWithString",
            "$r1 = \"something\"",
            "$i0 = 0",
            "if $r1 == $i1 goto label1",
            "if $r1 == $i2 goto label2",
            "if $r1 == $i3 goto label3",
            "goto label4",
            "label1:",
            "$i0 = 1",
            "goto label5",
            "label2:",
            "$i0 = 2",
            "goto label5",
            "label3:",
            "$i0 = 3",
            "goto label5",
            "label4:",
            "$i4 = 0 - 1",
            "$i0 = $i4",
            "label5:",
            "return")
        .collect(Collectors.toList());
  }

  @Test
  public void test() {
    SootMethod method = loadMethod(getMethodSignature());
    assertJimpleStmts(method, expectedBodyStmts());
  }
}
