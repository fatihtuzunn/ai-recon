
package ai.recon;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.EnhancedCapability;
import burp.api.montoya.logging.Logging;

import java.util.Set;
import static java.util.concurrent.Executors.newFixedThreadPool;

import static burp.api.montoya.EnhancedCapability.AI_FEATURES;
public class ReconExtension implements BurpExtension {
    @Override
    public void initialize(MontoyaApi api) {
        api.extension().setName("AI Recon Assistant");
        Logging logging = api.logging();

        ExecutorService executorService = newFixedThreadPool(3); // Save reference to thread pool

        var promptHandler = new ReconPromptHandler(api);

        var tab = new ReconTab(api, promptHandler, executorService);

        api.userInterface().registerSuiteTab("AI Recon", tab.getUiComponent());

        api.extension().registerUnloadingHandler(executorService::shutdownNow);
        api.logging().logToOutput("AI Recon Assistant loaded.");
    }
    @Override
    public Set<EnhancedCapability> enhancedCapabilities() {
        return Set.of(AI_FEATURES);
    }
}
