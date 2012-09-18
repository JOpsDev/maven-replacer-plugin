package com.google.code.maven_replacer_plugin;


import static java.util.Arrays.asList;
import static junit.framework.Assert.assertSame;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import com.google.code.maven_replacer_plugin.file.FileUtils;
import com.google.code.maven_replacer_plugin.include.FileSelector;

public class ReplacerMojoTest {

	private static final String REGEX_FLAG = "regex flag";
	private static final String FILE = "file";
	private static final boolean REGEX = true;
	private static final String OUTPUT_FILE = "output file";
	private static final int REGEX_PATTERN_FLAGS = 999;
	private static final String BASE_DIR = "base dir";
	private static final String TOKEN_VALUE_MAP = "token value map";
	private static final String TOKEN_FILE = "token file";
	private static final String VALUE_FILE = "value file";
	private static final String TOKEN = "token";
	private static final String VALUE = "value";

	private FileUtils fileUtils;
	private TokenReplacer tokenReplacer;
	private ReplacerFactory replacerFactory;
	private TokenValueMapFactory tokenValueMapFactory;
	private FileSelector fileSelector;
	private PatternFlagsFactory patternFlagsFactory;
	private ReplacerMojo mojo;
	private Log log;
	private List<String> regexFlags;
	private Replacer replacer;
	private OutputFilenameBuilder outputFilenameBuilder;
	private SummaryBuilder summaryBuilder;

	@Before
	public void setUp() throws Exception {
		fileUtils = mock(FileUtils.class);
		tokenReplacer = mock(TokenReplacer.class);
		replacerFactory = mock(ReplacerFactory.class);
		tokenValueMapFactory = mock(TokenValueMapFactory.class);
		fileSelector = mock(FileSelector.class);
		patternFlagsFactory = mock(PatternFlagsFactory.class);
		log = mock(Log.class);
		replacer = mock(Replacer.class);
		outputFilenameBuilder = mock(OutputFilenameBuilder.class);
		regexFlags = asList(REGEX_FLAG);
		summaryBuilder = mock(SummaryBuilder.class);

		when(replacerFactory.create()).thenReturn(replacer);
		when(patternFlagsFactory.buildFlags(regexFlags)).thenReturn(REGEX_PATTERN_FLAGS);

		mojo = new ReplacerMojo(fileUtils, tokenReplacer, replacerFactory, tokenValueMapFactory,
				fileSelector, patternFlagsFactory, outputFilenameBuilder, summaryBuilder) {
			@Override
			public Log getLog() {
				return log;
			}
		};
		when(outputFilenameBuilder.buildFrom(FILE, mojo)).thenReturn(OUTPUT_FILE);
	}

	@Test
	public void shouldReplaceContentsInReplacements() throws Exception {
		Replacement replacement = mock(Replacement.class);
		List<Replacement> replacements = asList(replacement);

		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setReplacements(replacements);
		mojo.setFile(FILE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();
		
		assertSame(FILE, mojo.getFile());
		verify(replacer).replace(replacements, REGEX, BASE_DIR + File.separator + FILE, OUTPUT_FILE, REGEX_PATTERN_FLAGS);
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder).print(log);
	}
	
	@Test
	public void shouldReplaceContentsInReplacementsButNotPrintSummaryIfQuiet() throws Exception {
		Replacement replacement = mock(Replacement.class);
		List<Replacement> replacements = asList(replacement);

		mojo.setQuiet(true);
		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setReplacements(replacements);
		mojo.setFile(FILE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		verify(replacer).replace(replacements, REGEX, BASE_DIR + File.separator + FILE, OUTPUT_FILE, REGEX_PATTERN_FLAGS);
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder, never()).print(log);
	}

	@Test
	public void shouldReplaceContentsInIncludeAndExcludes() throws Exception {
		List<String> includes = asList("include");
		List<String> excludes = asList("exclude");
		when(fileSelector.listIncludes(BASE_DIR, includes, excludes)).thenReturn(asList(FILE));

		mojo.setIncludes(includes);
		mojo.setExcludes(excludes);
		mojo.setToken(TOKEN);
		mojo.setValue(VALUE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		assertSame(mojo.getIncludes(), includes);
		assertSame(mojo.getExcludes(), excludes);
		verify(replacer).replace(argThat(replacementOf(VALUE, false, TOKEN)), eq(REGEX), eq(BASE_DIR  + File.separator + FILE),
			eq(OUTPUT_FILE), anyInt());
	}

	@Test
	public void shouldReplaceContentsInFilesToIncludeAndExclude() throws Exception {
		String includes = "include1, include2";
		String excludes = "exclude1, exclude2";
		when(fileSelector.listIncludes(BASE_DIR, asList("include1", "include2"), asList("exclude1", "exclude2"))).thenReturn(asList(FILE));

		mojo.setFilesToInclude(includes);
		mojo.setFilesToExclude(excludes);
		mojo.setToken(TOKEN);
		mojo.setValue(VALUE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		assertSame(mojo.getFilesToInclude(), includes);
		assertSame(mojo.getFilesToExclude(), excludes);
		verify(replacer).replace(argThat(replacementOf(VALUE, false, TOKEN)), eq(REGEX), eq(BASE_DIR + File.separator + FILE),
			eq(OUTPUT_FILE), anyInt());
	}

	@Test
	public void shouldReplaceContentsWithTokenValuesInMapWithComments() throws Exception {
		Replacement replacement = mock(Replacement.class);
		List<Replacement> replacements = asList(replacement);

		when(tokenValueMapFactory.contextsForFile(TOKEN_VALUE_MAP, true, false)).thenReturn(replacements);

		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setTokenValueMap(TOKEN_VALUE_MAP);
		mojo.setFile(FILE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		verify(replacer).replace(replacements, REGEX, BASE_DIR  + File.separator + FILE, OUTPUT_FILE, REGEX_PATTERN_FLAGS);
	}

	@Test
	public void shouldReplaceContentsWithTokenValuesInMapWithoutComments() throws Exception {
		Replacement replacement = mock(Replacement.class);
		List<Replacement> replacements = asList(replacement);

		when(tokenValueMapFactory.contextsForFile(TOKEN_VALUE_MAP, false, false)).thenReturn(replacements);

		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setTokenValueMap(TOKEN_VALUE_MAP);
		mojo.setFile(FILE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.setCommentsEnabled(false);
		mojo.execute();

		verify(replacer).replace(replacements, REGEX, BASE_DIR  + File.separator + FILE, OUTPUT_FILE, REGEX_PATTERN_FLAGS);
	}

	@Test
	public void shouldReplaceContentsWithTokenAndValueWithDelimiters() throws Exception {
		List<String> delimiters = asList("@", "${*}");
		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setFile(FILE);
		mojo.setToken(TOKEN);
		mojo.setValue(VALUE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.setDelimiters(delimiters);
		mojo.execute();

		assertThat(mojo.getDelimiters(), equalTo(delimiters));
		verify(replacer).replace(argThat(replacementOf(VALUE, false, "@" + TOKEN + "@", "${" + TOKEN + "}")), 
				eq(REGEX), eq(BASE_DIR  + File.separator + FILE), eq(OUTPUT_FILE), eq(REGEX_PATTERN_FLAGS));
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder).print(log);
	}
	
	@Test
	public void shouldReplaceContentsWithTokenAndValue() throws Exception {
		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setFile(FILE);
		mojo.setToken(TOKEN);
		mojo.setValue(VALUE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		verify(replacer).replace(argThat(replacementOf(VALUE, false, TOKEN)), eq(REGEX), eq(BASE_DIR  + File.separator + FILE),
			eq(OUTPUT_FILE), eq(REGEX_PATTERN_FLAGS));
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder).print(log);
	}
	
	@Test
	public void shouldReplaceContentsWithTokenAndValueUnescaped() throws Exception {
		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setFile(FILE);
		mojo.setToken(TOKEN);
		mojo.setValue(VALUE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.setUnescape(true);
		mojo.execute();

		assertTrue(mojo.isUnescape());
		verify(replacer).replace(argThat(replacementOf(VALUE, true, TOKEN)), eq(REGEX), eq(BASE_DIR  + File.separator + FILE),
			eq(OUTPUT_FILE), eq(REGEX_PATTERN_FLAGS));
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder).print(log);
	}

	@Test
	public void shouldReplaceContentsWithTokenValuesInTokenAndValueFiles() throws Exception {
		when(fileUtils.readFile(TOKEN_FILE)).thenReturn(TOKEN);
		when(fileUtils.readFile(VALUE_FILE)).thenReturn(VALUE);

		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setFile(FILE);
		mojo.setTokenFile(TOKEN_FILE);
		mojo.setValueFile(VALUE_FILE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		verify(replacer).replace(argThat(replacementOf(VALUE, false, TOKEN)), eq(REGEX), eq(BASE_DIR  + File.separator + FILE),
				eq(OUTPUT_FILE), eq(REGEX_PATTERN_FLAGS));
		verify(fileUtils).readFile(TOKEN_FILE);
		verify(fileUtils).readFile(VALUE_FILE);
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder).print(log);
	}

	@Test
	public void shouldReplaceContentsInReplacementsInSameFileWhenNoOutputFile() throws Exception {
		Replacement replacement = mock(Replacement.class);
		List<Replacement> replacements = asList(replacement);

		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setReplacements(replacements);
		mojo.setFile(FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		verify(replacer).replace(replacements, REGEX, BASE_DIR  + File.separator + FILE, OUTPUT_FILE,
			REGEX_PATTERN_FLAGS);
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder).print(log);
	}
	
	@Test
	public void shouldReplaceContentsWithVariableTokenValueMap() throws Exception {
		Replacement replacement = mock(Replacement.class);
		List<Replacement> replacements = asList(replacement);

		when(tokenValueMapFactory.contextsForVariable(TOKEN_VALUE_MAP, true, false)).thenReturn(replacements);
		mojo.setVariableTokenValueMap(TOKEN_VALUE_MAP);
		mojo.setFile(FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();

		verify(replacer).replace(replacements, true, BASE_DIR  + File.separator + FILE, OUTPUT_FILE, 0);
		verify(summaryBuilder).add(BASE_DIR + File.separator + FILE, OUTPUT_FILE, log);
		verify(summaryBuilder).print(log);
	}

	@Test
	public void shouldNotReplaceIfIgnoringMissingFilesAndFileNotExists() throws Exception {
		when(fileUtils.fileNotExists(BASE_DIR + File.separator + FILE)).thenReturn(true);
		mojo.setFile(FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.setIgnoreMissingFile(true);

		mojo.execute();
		verifyZeroInteractions(replacerFactory);
		verify(log).info(anyString());
		verify(summaryBuilder, never()).add(anyString(), anyString(), isA(Log.class));
		verify(summaryBuilder).print(log);
	}
	
	@Test
	public void shouldThrowExceptionWhenUsingIgnoreMissingFilesAndNoFileSpecified() throws Exception {
		mojo.setIgnoreMissingFile(true);
		try {
			mojo.execute();
			fail("Should have thrown exception");
		} catch (MojoExecutionException e) {
			verifyZeroInteractions(replacerFactory);
			verify(log).error("<ignoreMissingFile> only useable with <file>");
			verify(summaryBuilder, never()).add(anyString(), anyString(), isA(Log.class));
			verify(summaryBuilder).print(log);
		}
	}

	@Test
	public void shouldCreateNewInstancesOfDepenenciesOnConstructor() {
		new ReplacerMojo();
	}

	@Test (expected = MojoExecutionException.class)
	public void shouldRethrowIOExceptionsAsMojoExceptions() throws Exception {
		when(fileUtils.readFile(anyString())).thenThrow(new IOException());

		mojo.setRegexFlags(regexFlags);
		mojo.setRegex(REGEX);
		mojo.setFile(FILE);
		mojo.setTokenFile(TOKEN_FILE);
		mojo.setValueFile(VALUE_FILE);
		mojo.setOutputFile(OUTPUT_FILE);
		mojo.setBasedir(BASE_DIR);
		mojo.execute();
	}
	
	private BaseMatcher<List<Replacement>> replacementOf(final String value, final boolean unescape, final String... tokens) {
		return new BaseMatcher<List<Replacement>>() {
			@SuppressWarnings("unchecked")
			public boolean matches(Object arg0) {
				for (int i=0; i < tokens.length; i++) {
					Replacement replacement = ((List<Replacement>) arg0).get(i);
					if (!(tokens[i].equals(replacement.getToken()) && value.equals(replacement.getValue())
						&& unescape == replacement.isUnescape())) {
						return false;
					}
				}
				return true;
			}

			public void describeTo(Description desc) {
				desc.appendText("tokens=" + Arrays.asList(tokens) + " ");
				desc.appendText("value=" + value + " ");
				desc.appendText("unescape=" + unescape);
			}
		};
	}
}
