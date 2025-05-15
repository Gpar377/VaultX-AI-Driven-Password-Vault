package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final TestLibraryAccessors laccForTestLibraryAccessors = new TestLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>androidXCore</b> with <b>androidx.core:core</b> coordinates and
     * with version reference <b>androidXCoreVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getAndroidXCore() {
        return create("androidXCore");
    }

    /**
     * Dependency provider for <b>appCompat</b> with <b>androidx.appcompat:appcompat</b> coordinates and
     * with version reference <b>appCompatVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getAppCompat() {
        return create("appCompat");
    }

    /**
     * Dependency provider for <b>biometrics</b> with <b>androidx.biometric:biometric</b> coordinates and
     * with version reference <b>biometricsVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getBiometrics() {
        return create("biometrics");
    }

    /**
     * Dependency provider for <b>coil</b> with <b>io.coil-kt:coil</b> coordinates and
     * with version reference <b>coilVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getCoil() {
        return create("coil");
    }

    /**
     * Dependency provider for <b>constraintLayout</b> with <b>androidx.constraintlayout:constraintlayout</b> coordinates and
     * with version reference <b>constraintLayoutVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getConstraintLayout() {
        return create("constraintLayout");
    }

    /**
     * Dependency provider for <b>coordinatorLayout</b> with <b>androidx.coordinatorlayout:coordinatorlayout</b> coordinates and
     * with version reference <b>coordinatorLayoutVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getCoordinatorLayout() {
        return create("coordinatorLayout");
    }

    /**
     * Dependency provider for <b>coroutinesAndroid</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-android</b> coordinates and
     * with version reference <b>coroutinesVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getCoroutinesAndroid() {
        return create("coroutinesAndroid");
    }

    /**
     * Dependency provider for <b>coroutinesCore</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-core</b> coordinates and
     * with version reference <b>coroutinesVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getCoroutinesCore() {
        return create("coroutinesCore");
    }

    /**
     * Dependency provider for <b>documentfile</b> with <b>androidx.documentfile:documentfile</b> coordinates and
     * with version reference <b>documentfileVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getDocumentfile() {
        return create("documentfile");
    }

    /**
     * Dependency provider for <b>kotlinStdlib</b> with <b>org.jetbrains.kotlin:kotlin-stdlib-jdk8</b> coordinates and
     * with version reference <b>kotlinVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getKotlinStdlib() {
        return create("kotlinStdlib");
    }

    /**
     * Dependency provider for <b>kotpass</b> with <b>com.github.keemobile:kotpass</b> coordinates and
     * with version reference <b>kotpassVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getKotpass() {
        return create("kotpass");
    }

    /**
     * Dependency provider for <b>leakCanary</b> with <b>com.squareup.leakcanary:leakcanary-android</b> coordinates and
     * with version reference <b>leakCanaryVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getLeakCanary() {
        return create("leakCanary");
    }

    /**
     * Dependency provider for <b>lifecycleKtx</b> with <b>androidx.lifecycle:lifecycle-runtime-ktx</b> coordinates and
     * with version reference <b>lifecycleKtxVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getLifecycleKtx() {
        return create("lifecycleKtx");
    }

    /**
     * Dependency provider for <b>okio</b> with <b>com.squareup.okio:okio</b> coordinates and
     * with version reference <b>okioVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getOkio() {
        return create("okio");
    }

    /**
     * Dependency provider for <b>recyclerView</b> with <b>androidx.recyclerview:recyclerview</b> coordinates and
     * with version reference <b>recyclerViewVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getRecyclerView() {
        return create("recyclerView");
    }

    /**
     * Dependency provider for <b>timber</b> with <b>com.jakewharton.timber:timber</b> coordinates and
     * with version reference <b>timberVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getTimber() {
        return create("timber");
    }

    /**
     * Dependency provider for <b>zxcvbn</b> with <b>com.nulab-inc:zxcvbn</b> coordinates and
     * with version reference <b>zxcvbnVersion</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getZxcvbn() {
        return create("zxcvbn");
    }

    /**
     * Group of libraries at <b>test</b>
     */
    public TestLibraryAccessors getTest() {
        return laccForTestLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class TestLibraryAccessors extends SubDependencyFactory {

        public TestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>androidTestCoreKtx</b> with <b>androidx.test:core-ktx</b> coordinates and
         * with version reference <b>androidTestVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroidTestCoreKtx() {
            return create("test.androidTestCoreKtx");
        }

        /**
         * Dependency provider for <b>androidTestExt</b> with <b>androidx.test.ext:junit-ktx</b> coordinates and
         * with version reference <b>androidTestVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroidTestExt() {
            return create("test.androidTestExt");
        }

        /**
         * Dependency provider for <b>androidTestRules</b> with <b>androidx.test:rules</b> coordinates and
         * with version reference <b>androidTestVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroidTestRules() {
            return create("test.androidTestRules");
        }

        /**
         * Dependency provider for <b>androidTestRunner</b> with <b>androidx.test:runner</b> coordinates and
         * with version reference <b>androidTestVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroidTestRunner() {
            return create("test.androidTestRunner");
        }

        /**
         * Dependency provider for <b>coroutinesTest</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-test</b> coordinates and
         * with version reference <b>coroutinesVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCoroutinesTest() {
            return create("test.coroutinesTest");
        }

        /**
         * Dependency provider for <b>espressoIntents</b> with <b>androidx.test.espresso:espresso-intents</b> coordinates and
         * with version reference <b>espressoVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEspressoIntents() {
            return create("test.espressoIntents");
        }

        /**
         * Dependency provider for <b>junit</b> with <b>junit:junit</b> coordinates and
         * with version reference <b>junitVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() {
            return create("test.junit");
        }

        /**
         * Dependency provider for <b>kaspresso</b> with <b>com.kaspersky.android-components:kaspresso</b> coordinates and
         * with version reference <b>kaspressoVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKaspresso() {
            return create("test.kaspresso");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>androidGradlePlugin</b> with value <b>8.7.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidGradlePlugin() { return getVersion("androidGradlePlugin"); }

        /**
         * Version alias <b>androidTestVersion</b> with value <b>1.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidTestVersion() { return getVersion("androidTestVersion"); }

        /**
         * Version alias <b>androidXCoreVersion</b> with value <b>1.12.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidXCoreVersion() { return getVersion("androidXCoreVersion"); }

        /**
         * Version alias <b>appCompatVersion</b> with value <b>1.6.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAppCompatVersion() { return getVersion("appCompatVersion"); }

        /**
         * Version alias <b>biometricsVersion</b> with value <b>1.0.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBiometricsVersion() { return getVersion("biometricsVersion"); }

        /**
         * Version alias <b>coilVersion</b> with value <b>2.4.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoilVersion() { return getVersion("coilVersion"); }

        /**
         * Version alias <b>constraintLayoutVersion</b> with value <b>2.1.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getConstraintLayoutVersion() { return getVersion("constraintLayoutVersion"); }

        /**
         * Version alias <b>coordinatorLayoutVersion</b> with value <b>1.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoordinatorLayoutVersion() { return getVersion("coordinatorLayoutVersion"); }

        /**
         * Version alias <b>coroutinesVersion</b> with value <b>1.7.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoroutinesVersion() { return getVersion("coroutinesVersion"); }

        /**
         * Version alias <b>documentfileVersion</b> with value <b>1.0.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getDocumentfileVersion() { return getVersion("documentfileVersion"); }

        /**
         * Version alias <b>espressoVersion</b> with value <b>3.4.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEspressoVersion() { return getVersion("espressoVersion"); }

        /**
         * Version alias <b>junitVersion</b> with value <b>4.13.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunitVersion() { return getVersion("junitVersion"); }

        /**
         * Version alias <b>kaspressoVersion</b> with value <b>1.4.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKaspressoVersion() { return getVersion("kaspressoVersion"); }

        /**
         * Version alias <b>kotlinAndroidPlugin</b> with value <b>1.9.20-Beta2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKotlinAndroidPlugin() { return getVersion("kotlinAndroidPlugin"); }

        /**
         * Version alias <b>kotlinVersion</b> with value <b>1.9.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKotlinVersion() { return getVersion("kotlinVersion"); }

        /**
         * Version alias <b>kotpassVersion</b> with value <b>0.6.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKotpassVersion() { return getVersion("kotpassVersion"); }

        /**
         * Version alias <b>leakCanaryVersion</b> with value <b>2.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLeakCanaryVersion() { return getVersion("leakCanaryVersion"); }

        /**
         * Version alias <b>lifecycleKtxVersion</b> with value <b>2.6.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLifecycleKtxVersion() { return getVersion("lifecycleKtxVersion"); }

        /**
         * Version alias <b>okioVersion</b> with value <b>3.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getOkioVersion() { return getVersion("okioVersion"); }

        /**
         * Version alias <b>recyclerViewVersion</b> with value <b>1.3.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRecyclerViewVersion() { return getVersion("recyclerViewVersion"); }

        /**
         * Version alias <b>timberVersion</b> with value <b>4.7.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTimberVersion() { return getVersion("timberVersion"); }

        /**
         * Version alias <b>zxcvbnVersion</b> with value <b>1.3.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getZxcvbnVersion() { return getVersion("zxcvbnVersion"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {
        private final AndroidPluginAccessors paccForAndroidPluginAccessors = new AndroidPluginAccessors(providers, config);
        private final KotlinPluginAccessors paccForKotlinPluginAccessors = new KotlinPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.android</b>
         */
        public AndroidPluginAccessors getAndroid() {
            return paccForAndroidPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.kotlin</b>
         */
        public KotlinPluginAccessors getKotlin() {
            return paccForKotlinPluginAccessors;
        }

    }

    public static class AndroidPluginAccessors extends PluginFactory {

        public AndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>android.application</b> with plugin id <b>com.android.application</b> and
         * with version reference <b>androidGradlePlugin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getApplication() { return createPlugin("android.application"); }

    }

    public static class KotlinPluginAccessors extends PluginFactory {

        public KotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>kotlin.android</b> with plugin id <b>org.jetbrains.kotlin.android</b> and
         * with version reference <b>kotlinAndroidPlugin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getAndroid() { return createPlugin("kotlin.android"); }

    }

}
