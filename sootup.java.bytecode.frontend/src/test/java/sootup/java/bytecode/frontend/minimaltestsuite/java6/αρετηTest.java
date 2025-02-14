package sootup.java.bytecode.frontend.minimaltestsuite.java6;

import categories.TestCategories;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.java.bytecode.frontend.minimaltestsuite.MinimalBytecodeTestSuiteBase;

/** @author Kaustubh Kelkar */
@Tag(TestCategories.JAVA_8_CATEGORY)
public class αρετηTest extends MinimalBytecodeTestSuiteBase {
  @Override
  public MethodSignature getMethodSignature() {
    return identifierFactory.getMethodSignature(
        getDeclaredClassSignature(), "αρετηAsClassName", "void", Collections.emptyList());
  }

  /**
   *
   *
   * <pre>
   * public class αρετη {
   *     public void αρετηAsClassName(){
   *         System.out.println("this is αρετη class");
   *     }
   * }
   * </pre>
   */
  @Override
  public List<String> expectedBodyStmts() {
    return Stream.of(
            "this := @this: \\u03b1\\u03c1\\u03b5\\u03c4\\u03b7",
            "$stack1 = <java.lang.System: java.io.PrintStream out>",
            "virtualinvoke $stack1.<java.io.PrintStream: void println(java.lang.String)>(\"this is \\u03b1\\u03c1\\u03b5\\u03c4\\u03b7 class\")",
            "return")
        .collect(Collectors.toList());
  }

  @Test
  // test only works on a filesystem that supports unicode
  public void test() {
    SootMethod method = loadMethod(getMethodSignature());
    assertJimpleStmts(method, expectedBodyStmts());
  }
}
