#!/bin/bash

# ERI Project SVN Update, Maven Build and Tomcat Deploy Script
# Usage: ./builds.sh

# Change to project directory (where pom.xml is located)
cd /opt/eri-project/eri-backend/ERI

echo "[1] SVN Update..."
svn update

echo "[2] Maven Build..."
sudo mvn clean package -Pprod -DskipTests

echo "[3] Tomcat Deploy..."
# Stop Tomcat if running
echo "Stopping Tomcat..."
sudo systemctl stop tomcat10

# Backup existing deployment
echo "Backing up existing deployment..."
sudo cp /opt/apache-tomcat-10.1.19/webapps/ERI.war /opt/apache-tomcat-10.1.19/webapps/ERI.war.backup.$(date +%Y%m%d_%H%M%S) 2>/dev/null || echo "No existing deployment to backup"

# Deploy new WAR file
echo "Deploying new WAR file..."
sudo cp target/ERI-0.0.1-SNAPSHOT.war /opt/apache-tomcat-10.1.19/webapps/ERI.war

# Start Tomcat
echo "Starting Tomcat..."
sudo systemctl start tomcat10

# Wait for Tomcat to start
echo "Waiting for Tomcat to start..."
sleep 15

# Check if Tomcat is running
if sudo systemctl is-active --quiet tomcat10; then
    echo "✅ Tomcat deployment successful!"
else
    echo "❌ Tomcat deployment failed!"
    exit 1
fi

echo "[Complete] SVN update, Maven build and Tomcat deploy finished."
