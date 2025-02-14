/* Qilin - a Java Pointer Analysis Framework
 * Copyright (C) 2021-2030 Qilin developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3.0 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/lgpl-3.0.en.html>.
 */

package qilin.test.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.junit.BeforeClass;
import qilin.core.PTA;
import qilin.driver.PTAFactory;
import qilin.driver.PTAOption;
import qilin.driver.PTAPattern;
import qilin.pta.PTAConfig;
import qilin.util.PTAUtils;
import sootup.core.views.View;

public abstract class JunitTests {
  protected static String appPath, jrePath, refLogPath;
  protected static boolean isSetUp = false;

  @BeforeClass
  public static void setUp() throws IOException {
    if (isSetUp) {
      return;
    }
    File rootDir = new File("../");
    File testDir =
        new File(
            rootDir, "sootup.qilin" + File.separator + "target" + File.separator + "test-classes");
    appPath = testDir.getCanonicalPath();
    System.out.println("APP_PATH:" + appPath);
    File refLogDir =
        new File(
            rootDir,
            "sootup.qilin"
                + File.separator
                + "src"
                + File.separator
                + "test"
                + File.separator
                + "java"
                + File.separator
                + "qilin"
                + File.separator
                + "microben"
                + File.separator
                + "core"
                + File.separator
                + "reflog");
    refLogPath = refLogDir.getCanonicalPath();
    File jreFile =
        new File(
            ".."
                + File.separator
                + "artifact"
                + File.separator
                + "benchmarks"
                + File.separator
                + "JREs"
                + File.separator
                + "jre1.6.0_45"
            //    + "jre1.8.0_121_debug"
            );
    jrePath = jreFile.getCanonicalPath();
    String[] args = generateArgumentsx();
    PTAOption ptaOption = new PTAOption();
    ptaOption.parseCommandLine(args);
    isSetUp = true;
  }

  public PTA run(String mainClass) {
    return run(mainClass, "insens");
  }

  public PTA run(String mainClass, String ptaPattern) {
    PTAConfig.v().getAppConfig().MAIN_CLASS = mainClass;
    PTAConfig.v().getPtaConfig().ptaPattern = new PTAPattern(ptaPattern);
    PTAConfig.v().getPtaConfig().ptaName = PTAConfig.v().getPtaConfig().ptaPattern.toString();
    System.out.println(PTAConfig.v().getAppConfig().APP_PATH);
    View view = PTAUtils.createView();
    PTA pta = PTAFactory.createPTA(PTAConfig.v().getPtaConfig().ptaPattern, view, mainClass);
    pta.pureRun();
    return pta;
  }

  public static String[] generateArgumentsx() {
    return new String[] {
      "-singleentry",
      "-apppath",
      appPath,
      "-mainclass",
      "qilin.microben.core.exception.SimpleException",
      "-se",
      "-jre=" + jrePath,
      "-clinit=ONFLY",
      "-lcs",
      "-mh",
      "-pae",
      "-pe",
      "-reflectionlog",
      refLogPath + File.separator + "Reflection.log"
    };
  }

  protected void checkAssertions(PTA pta) {
    Set<IAssertion> aliasAssertionSet = AssertionsParser.retrieveQueryInfo(pta);
    for (IAssertion mAssert : aliasAssertionSet) {
      boolean answer = mAssert.check();
      System.out.println("Assertion is " + answer);
      assertTrue(answer);
    }
  }
}
