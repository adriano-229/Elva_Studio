#!/bin/bash

echo "========================================="
echo "Compilando proyecto..."
echo "========================================="
cd "/home/adriano/repos/Elva_Studio/integrador_2/My Car/mycar"

./mvnw clean compile -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✓ Compilación exitosa"
else
    echo "✗ Error en compilación"
    exit 1
fi

echo ""
echo "========================================="
echo "Ejecutando tests..."
echo "========================================="

./mvnw test -Dtest=PagoServiceTest -q

if [ $? -eq 0 ]; then
    echo "✓ Tests exitosos"
else
    echo "✗ Algunos tests fallaron"
fi

echo ""
echo "========================================="
echo "Resumen de resultados"
echo "========================================="
./mvnw test -Dtest=PagoServiceTest 2>&1 | grep -E "Tests run:|BUILD"

