package uk.co.threebugs.service;

import org.junit.Test;

import java.nio.file.Paths;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocalFileServiceImplTest {
    @Test
    public void shouldFilterLocalFiles() throws Exception {
        final Set<String> strings = new LocalFileServiceImpl().listLocalFiles(Paths.get("testFiles/localFiles"), false);
        assertThat(strings.size(), is(equalTo(1)));
        assertThat(strings.iterator()
                .next(), is(equalTo("spx.csv")));
    }
}
