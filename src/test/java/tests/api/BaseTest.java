package tests.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.PropertyProvider;
import steps.Steps;

public class BaseTest {
    final String URL = PropertyProvider.getInstance().getProperties("url");
    Steps steps = new Steps();
    ObjectMapper objectMapper = new ObjectMapper();
}
