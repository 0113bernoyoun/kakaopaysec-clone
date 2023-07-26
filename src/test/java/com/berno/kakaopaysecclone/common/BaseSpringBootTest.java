package com.berno.kakaopaysecclone.common;

import com.berno.kakaopaysecclone.common.config.DBUnitConfig;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.berno.kakaopaysecclone.common.BaseSpringBootTest.STOCK;

@Disabled
@Import(DBUnitConfig.class)
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@DatabaseSetup(value = {STOCK})
@DatabaseTearDown(value = {STOCK})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class BaseSpringBootTest {
    @Autowired
    protected MockMvc mockMvc;
    protected static final String BASE_PATH = "/common/dbunit";
    protected static final String STOCK = BASE_PATH + "/stock.xml";
}
