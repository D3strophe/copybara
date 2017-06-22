import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.copybara.ChangeMessage.parseMessage;
import static com.google.copybara.testing.git.GitTestUtil.getGitEnv;
import com.google.common.truth.Truth;
import com.google.copybara.ChangeMessage;
import com.google.copybara.git.GitRepository.GitLogEntry;
import java.time.ZoneId;
import java.time.ZonedDateTime;
    return new GitRepository(path, /*workTree=*/null, /*verbose=*/true, getGitEnv());
    assertThat(repo().log(ref).run()).hasSize(expected);
    assertThat(parseMessage(lastCommit(branch).getBody())
        .labelsAsMultimap()).containsEntry(DummyOrigin.LABEL_NAME, originRef);
    assertThat(lastCommit(branch).getAuthor()).isEqualTo(author);
  }

  private GitLogEntry lastCommit(String ref) throws RepoException {
    return getOnlyElement(repo().log(ref).withLimit(1).run());
        firstCommitWriter(),
        firstCommitWriter(),
        firstCommitWriter(),
        newWriter(),
        firstCommitWriter(),
    process(firstCommitWriter(), ref);
    process(newWriter(), ref);
        newWriter(),
        newWriter(),
        firstCommitWriter(),
        newWriter(),
        newWriter(),
    Writer writer1 = destinationFirstCommit().newWriter(firstGlob, /*dryRun=*/false);
    Writer writer2 = destination().newWriter(Glob.createGlob(ImmutableList.of("baz/**")),
        /*dryRun=*/false);
    assertThat(destination().newWriter(firstGlob, /*dryRun=*/false)
                   .getDestinationStatus(ref1.getLabelName(), null).getBaseline())
        .isEqualTo(ref1.asString());
    assertThat(writer2.getDestinationStatus(ref2.getLabelName(), null).getBaseline())
        .isEqualTo(ref2.asString());
        firstCommitWriter();
    assertThat(writer.getDestinationStatus(DummyOrigin.LABEL_NAME, null)).isNull();
    writer = newWriter();
    assertThat(writer.getDestinationStatus(DummyOrigin.LABEL_NAME, null).getBaseline())
        .isEqualTo("first_commit");
    writer = newWriter();
    assertThat(writer.getDestinationStatus(DummyOrigin.LABEL_NAME, null).getBaseline())
        .isEqualTo("second_commit");
        firstCommitWriter(),
    assertThat(newWriter().getDestinationStatus(DummyOrigin.LABEL_NAME, null).getBaseline())
    Truth.assertThat(checkPreviousImportReferenceMultipleParents()).isEqualTo("b2-origin");
  }
  @Test
  public void previousImportReferenceIsBeforeACommitWithMultipleParents_first_parent()
      throws Exception {
    options.gitDestination.lastRevFirstParent = true;
    Truth.assertThat(checkPreviousImportReferenceMultipleParents()).isEqualTo("b1-origin");
  }

  private String checkPreviousImportReferenceMultipleParents()
      throws IOException, RepoException, ValidationException {
    fetch = "b1";
    push = "b1";
    Files.write(scratchTree.resolve("master" + ".file"), ("master\n\n"
        + DummyOrigin.LABEL_NAME + ": should_not_happen").getBytes(UTF_8));
    scratchRepo.add().files("master" + ".file").run();
    scratchRepo.simpleCommand("commit", "-m", "master\n\n"
        + DummyOrigin.LABEL_NAME + ": should_not_happen");
    scratchRepo.simpleCommand("branch", "b1");
    scratchRepo.simpleCommand("branch", "b2");
    branchChange(scratchTree, scratchRepo, "b2", "b2-1\n\n"
        + DummyOrigin.LABEL_NAME + ": b2-origin");
    branchChange(scratchTree, scratchRepo, "b1", "b1-1\n\n"
        + DummyOrigin.LABEL_NAME + ": b1-origin");
    branchChange(scratchTree, scratchRepo, "b1", "b1-2");
    branchChange(scratchTree, scratchRepo, "b1", "b2-2");

    scratchRepo.simpleCommand("checkout", "b1");
    return newWriter().getDestinationStatus(DummyOrigin.LABEL_NAME, null).getBaseline();
  }
  private void branchChange(Path scratchTree, GitRepository scratchRepo, final String branch,
      String msg) throws RepoException, IOException {
    scratchRepo.simpleCommand("checkout", branch);
    Files.write(scratchTree.resolve(branch + ".file"), msg.getBytes(UTF_8));
    scratchRepo.add().files(branch + ".file").run();
    scratchRepo.simpleCommand("commit", "-m", msg);
        firstCommitWriter(),
        new DummyRevision("first_commit").withTimestamp(timeFromEpoch(1414141414)));
    GitTesting.assertAuthorTimestamp(repo(), "master", timeFromEpoch(1414141414));
        newWriter(),
        new DummyRevision("second_commit").withTimestamp(timeFromEpoch(1515151515)));
    GitTesting.assertAuthorTimestamp(repo(), "master", timeFromEpoch(1515151515));
  }

  static ZonedDateTime timeFromEpoch(long time) {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.of("-07:00"));
        firstCommitWriter(),
        firstCommitWriter(),
        new DummyRevision("first_commit").withTimestamp(timeFromEpoch(1414141414)));
        newWriter(),
        new DummyRevision("second_commit").withTimestamp(timeFromEpoch(1414141490)));
        firstCommitWriter(),
        new DummyRevision("first_commit").withTimestamp(timeFromEpoch(1414141414)));
        newWriter(),
        new DummyRevision("second_commit").withTimestamp(timeFromEpoch(1414141490)));
        firstCommitWriter(),
        firstCommitWriter(),
        .withTimestamp(timeFromEpoch(1414141414));
        firstCommitWriter(),
  /**
   * This test reproduces an issue where the author timestamp has subseconds and, as a result,
   * before the fix the change was committed with the (incorrect) date '2017-04-12T12:19:00-07:00',
   * instead of '2017-06-01T12:19:00-04:00'.
   */
  @Test
  public void authorDateWithSubsecondsCorrectlyPopulated() throws Exception {
    fetch = "master";
    push = "master";

    Files.write(workdir.resolve("test.txt"), "some content".getBytes());

    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(1496333940012L), ZoneId.of("-04:00"));
    DummyRevision firstCommit = new DummyRevision("first_commit")
        .withAuthor(new Author("Foo Bar", "foo@bar.com"))
        .withTimestamp(zonedDateTime);
    process(
        firstCommitWriter(),
        firstCommit);

    String authorDate = git("log", "-1", "--pretty=%aI");

    assertThat(authorDate).isEqualTo("2017-06-01T12:19:00-04:00\n");
  }

        newWriter(),
        newWriter(),
    process(firstCommitWriter(), ref);
    String firstCommit = repo().parseRef("HEAD");
    process(newWriter(), ref);
        newWriter(), ref, firstCommit);
    process(firstCommitWriter(), ref);
    String firstCommit = repo().parseRef("HEAD");
    process(newWriter(), ref);
        newWriter(), ref, firstCommit);
    process(firstCommitWriter(), ref);
    String firstCommit = repo().parseRef("HEAD");
    process(newWriter(), ref);
        newWriter(), ref, firstCommit);
  @Test
  public void processWithBaselineNotFound() throws Exception {
    fetch = "master";
    push = "master";
    DummyRevision ref = new DummyRevision("origin_ref");

    Files.write(workdir.resolve("test.txt"), "some content".getBytes());
    process(firstCommitWriter(), ref);

    Files.write(workdir.resolve("test.txt"), "more content".getBytes());
    thrown.expect(RepoException.class);
    thrown.expectMessage("Cannot find baseline 'I_dont_exist' from fetch reference 'master'");
    processWithBaseline(newWriter(), ref, "I_dont_exist");
  }

  @Test
  public void processWithBaselineNotFoundMasterNotFound() throws Exception {
    fetch = "test_test_test";
    push = "master";

    Files.write(workdir.resolve("test.txt"), "more content".getBytes());
    thrown.expect(RepoException.class);
    thrown.expectMessage(
        "Cannot find baseline 'I_dont_exist' and fetch reference 'test_test_test'");
    processWithBaseline(firstCommitWriter(), new DummyRevision("origin_ref"), "I_dont_exist");
  }

        firstCommitWriter();
    String firstCommitHash = repo().parseRef("refs_for_master");
    assertThat(repo().parseRef("refs_for_master~1")).isEqualTo(firstCommitHash);
    process(firstCommitWriter(), ref);
    process(newWriter(), ref);
  @Test
  public void testDryRun() throws Exception {
    fetch = "master";
    push = "master";

    Files.write(workdir.resolve("test.txt"), "some content".getBytes());

    Path scratchTree = Files.createTempDirectory("GitDestinationTest-testLocalRepo");
    Files.write(scratchTree.resolve("foo"), "foo\n".getBytes(UTF_8));
    repo().withWorkTree(scratchTree).add().force().files("foo").run();
    repo().withWorkTree(scratchTree).simpleCommand("commit", "-a", "-m", "change");

    Writer writer = destination().newWriter(destinationFiles, /*dryRun=*/ true);
    process(writer, new DummyRevision("origin_ref1"));

    GitTesting.assertThatCheckout(repo(), "master")
        .containsFile("foo", "foo\n")
        .containsNoMoreFiles();

    // Run again without dry run
    writer = destination().newWriter(destinationFiles, /*dryRun=*/ false);
    process(writer, new DummyRevision("origin_ref1"));

    GitTesting.assertThatCheckout(repo(), "master")
        .containsFile("test.txt", "some content")
        .containsNoMoreFiles();
  }

  @Test
  public void testMultipleRefs() throws Exception {
    Path scratchTree = Files.createTempDirectory("GitDestinationTest-testLocalRepo");
    Files.write(scratchTree.resolve("base"), "base\n".getBytes(UTF_8));
    repo().withWorkTree(scratchTree).add().force().files("base").run();
    repo().withWorkTree(scratchTree).simpleCommand("commit", "-a", "-m", "base");

    GitRevision master = repo().resolveReference("master", /*contextRef=*/null);

    repo().simpleCommand("update-ref", "refs/other/master", master.getSha1());

    checkLocalRepo(true);
  }

    Writer writer = newWriter();
        getGitEnv());
    ImmutableList<GitLogEntry> entries = localRepo.log("HEAD").run();
    assertThat(entries.get(0).getBody()).isEqualTo(""
        + "test summary\n"
        + "DummyOrigin-RevId: origin_ref2\n");

    assertThat(entries.get(1).getBody()).isEqualTo(""
        + "DummyOrigin-RevId: origin_ref1\n");

    assertThat(entries.get(2).getBody()).isEqualTo("change\n");

  @Test
  public void testLabelInSameLabelGroupGroup() throws Exception {
    fetch = "master";
    push = "master";
    Writer writer = firstCommitWriter();
    Files.write(workdir.resolve("test.txt"), "".getBytes());
    DummyRevision rev = new DummyRevision("first_commit");
    String msg = "This is a message\n"
        + "\n"
        + "That already has a label\n"
        + "THE_LABEL: value\n";
    writer.write(new TransformResult(workdir, rev, rev.getAuthor(), msg, rev), console);

    String body = lastCommit("HEAD").getBody();
    assertThat(body).isEqualTo("This is a message\n"
        + "\n"
        + "That already has a label\n"
        + "THE_LABEL: value\n"
        + "DummyOrigin-RevId: first_commit\n");
    // Double check that we can access it as a label.
    assertThat(ChangeMessage.parseMessage(body).labelsAsMultimap())
        .containsEntry("DummyOrigin-RevId", "first_commit");
  }

        firstCommitWriter(), ref1);
    process(newWriter(), ref2);

  private Writer newWriter() throws ValidationException {
    return destination().newWriter(destinationFiles, /*dryRun=*/ false);
  }

  private Writer firstCommitWriter() throws ValidationException {
    return destinationFirstCommit().newWriter(destinationFiles, /*dryRun=*/ false);
  }