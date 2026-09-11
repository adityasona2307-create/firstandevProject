// Top-level build
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Alias ko hatakar direct ID aur version (2.52) laga diya hai
    id("com.google.dagger.hilt.android") version "2.57.1" apply false

    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}