/*
 * Copyright (C) 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.copybara.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.collect.ImmutableList;
import com.google.copybara.util.DiffUtil.DiffFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/** A utility class to automatically generate patch files */
final class AutoPatchUtil {

  private AutoPatchUtil() {}

  /**
   * Given two paths, generates patch files per-file
   *
   * <p>Does not generate any patch files where there is no diff. Patch files are generated using
   * git diff.
   */
  public static void generatePatchFiles(
      Path one,
      Path other,
      Path writePath,
      boolean verbose,
      Map<String, String> environment,
      String patchFilePrefix)
      throws IOException, InsideGitDirException {

    ImmutableList<DiffFile> diffFiles = DiffUtil.diffFiles(one, other, verbose, environment);
    for (DiffFile diffFile : diffFiles) {
      String fileName = diffFile.getName();
      Path onePath = one.resolve(fileName);
      Path otherPath = other.resolve(fileName);
      if (!Files.exists(onePath)) {
        continue;
      }
      String diffString =
          new String(
              DiffUtil.diffFileWithIgnoreCrAtEol(onePath, otherPath, verbose, environment), UTF_8);
      Path patchFilePath = writePath.resolve(fileName);
      Files.createDirectories(patchFilePath.getParent());
      Files.writeString(patchFilePath, patchFilePrefix.concat(diffString));
    }
  }
}