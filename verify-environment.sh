#!/bin/bash

# Script to verify build environment for Instant Massive Structures Mod (Fabric Port)
# This checks that you have all the necessary tools and access to build the mod

echo "========================================"
echo "IMSM Fabric Port - Environment Check"
echo "========================================"
echo ""

# Check Java version
echo "1. Checking Java version..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    JAVA_MAJOR=$(echo $JAVA_VERSION | cut -d'.' -f1)
    
    if [ "$JAVA_MAJOR" -ge "21" ]; then
        echo "   ✅ Java $JAVA_VERSION found (requirement: Java 21+)"
    else
        echo "   ❌ Java $JAVA_VERSION found, but Java 21+ is required"
        echo "   Download from: https://adoptium.net/"
        exit 1
    fi
else
    echo "   ❌ Java not found. Please install Java 21 or higher"
    echo "   Download from: https://adoptium.net/"
    exit 1
fi

echo ""

# Check Gradle
echo "2. Checking Gradle (via gradlew)..."
if [ -f "./gradlew" ]; then
    echo "   ✅ Gradle wrapper found"
else
    echo "   ❌ Gradle wrapper not found. Are you in the project root?"
    exit 1
fi

echo ""

# Check network access to maven.fabricmc.net
echo "3. Checking access to maven.fabricmc.net..."
if curl -s --head --max-time 5 https://maven.fabricmc.net/ | head -n 1 | grep "HTTP" > /dev/null; then
    echo "   ✅ maven.fabricmc.net is accessible"
else
    echo "   ⚠️  maven.fabricmc.net is not accessible"
    echo "   This is required to download Fabric Loom and dependencies"
    echo "   Check your internet connection and firewall settings"
    exit 1
fi

echo ""

# Check if we can resolve minecraft assets
echo "4. Checking access to piston-meta.mojang.com..."
if curl -s --head --max-time 5 https://piston-meta.mojang.com/ | head -n 1 | grep "HTTP" > /dev/null; then
    echo "   ✅ piston-meta.mojang.com is accessible"
else
    echo "   ⚠️  piston-meta.mojang.com is not accessible"
    echo "   This may cause issues downloading Minecraft"
fi

echo ""

# Check disk space
echo "5. Checking disk space..."
AVAILABLE_SPACE=$(df -h . | awk 'NR==2 {print $4}' | sed 's/G//')
if (( $(echo "$AVAILABLE_SPACE > 5" | bc -l) )); then
    echo "   ✅ Sufficient disk space available"
else
    echo "   ⚠️  Low disk space. At least 5GB recommended"
fi

echo ""

# Try a test build
echo "6. Testing Gradle configuration..."
if ./gradlew tasks --no-daemon > /dev/null 2>&1; then
    echo "   ✅ Gradle configuration is valid"
else
    echo "   ❌ Gradle configuration has errors"
    echo "   Run './gradlew tasks' for more details"
    exit 1
fi

echo ""
echo "========================================"
echo "✅ Environment check passed!"
echo "========================================"
echo ""
echo "You can now build the mod with:"
echo "  ./gradlew build"
echo ""
echo "Or run it in development with:"
echo "  ./gradlew runClient"
echo ""
echo "Note: The mod is not yet fully ported."
echo "See PORTING_GUIDE.md and TODO.md for details."
echo ""
