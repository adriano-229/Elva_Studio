#!/bin/bash

# Script de verificación completa del sistema de pagos

echo "======================================================"
echo "   VERIFICACIÓN SISTEMA DE PAGOS Y FACTURACIÓN"
echo "======================================================"
echo ""

cd "/home/adriano/repos/Elva_Studio/integrador_2/My Car/mycar"

# 1. Verificar que todos los archivos existen
echo "1. Verificando archivos modificados/creados..."
files=(
    "src/main/java/com/example/mycar/entities/DetalleFactura.java"
    "src/main/java/com/example/mycar/entities/Alquiler.java"
    "src/main/java/com/example/mycar/entities/CostoVehiculo.java"
    "src/main/java/com/example/mycar/services/impl/PagoServiceImpl.java"
    "src/main/java/com/example/mycar/error/AlquilerYaFacturadoException.java"
    "src/main/java/com/example/mycar/error/FacturaYaAprobadaException.java"
    "src/main/java/com/example/mycar/error/FacturaYaAnuladaException.java"
    "src/main/java/com/example/mycar/error/FacturaNoEncontradaException.java"
    "src/main/java/com/example/mycar/error/VehiculoSinCostoException.java"
    "src/main/java/com/example/mycar/error/AlquilerNoEncontradoException.java"
    "src/test/java/com/example/mycar/services/PagoServiceTest.java"
)

all_exist=true
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "   ✓ $file"
    else
        echo "   ✗ $file (NO ENCONTRADO)"
        all_exist=false
    fi
done

if [ "$all_exist" = true ]; then
    echo "   ✓ Todos los archivos existen"
else
    echo "   ✗ Algunos archivos no existen"
    exit 1
fi

echo ""

# 2. Verificar uso de BigDecimal
echo "2. Verificando uso de BigDecimal..."
if grep -q "private BigDecimal subtotal" src/main/java/com/example/mycar/entities/DetalleFactura.java; then
    echo "   ✓ DetalleFactura usa BigDecimal"
else
    echo "   ✗ DetalleFactura NO usa BigDecimal"
fi

if grep -q "private BigDecimal costoCalculado" src/main/java/com/example/mycar/entities/Alquiler.java; then
    echo "   ✓ Alquiler usa BigDecimal"
else
    echo "   ✗ Alquiler NO usa BigDecimal"
fi

if grep -q "private BigDecimal costo" src/main/java/com/example/mycar/entities/CostoVehiculo.java; then
    echo "   ✓ CostoVehiculo usa BigDecimal"
else
    echo "   ✗ CostoVehiculo NO usa BigDecimal"
fi

echo ""

# 3. Verificar @Version para concurrencia
echo "3. Verificando control de concurrencia..."
if grep -q "@Version" src/main/java/com/example/mycar/entities/Alquiler.java; then
    echo "   ✓ Alquiler tiene @Version"
else
    echo "   ✗ Alquiler NO tiene @Version"
fi

echo ""

# 4. Verificar logging
echo "4. Verificando logging..."
if grep -q "@Slf4j" src/main/java/com/example/mycar/services/impl/PagoServiceImpl.java; then
    echo "   ✓ PagoServiceImpl tiene @Slf4j"
else
    echo "   ✗ PagoServiceImpl NO tiene @Slf4j"
fi

if grep -q "log.info" src/main/java/com/example/mycar/services/impl/PagoServiceImpl.java; then
    echo "   ✓ PagoServiceImpl usa log.info"
else
    echo "   ✗ PagoServiceImpl NO usa log.info"
fi

echo ""

# 5. Contar tests
echo "5. Verificando tests..."
test_count=$(grep -c "@Test" src/test/java/com/example/mycar/services/PagoServiceTest.java)
echo "   ✓ $test_count tests encontrados"

if [ $test_count -ge 16 ]; then
    echo "   ✓ Número de tests adecuado (>= 16)"
else
    echo "   ⚠ Menos tests de lo esperado (< 16)"
fi

echo ""

# 6. Verificar documentación
echo "6. Verificando documentación..."
docs=(
    "SISTEMA_PAGOS_FACTURACION.md"
    "CORRECCIONES_REALIZADAS.md"
    "RESUMEN_EJECUTIVO.md"
)

for doc in "${docs[@]}"; do
    if [ -f "$doc" ]; then
        lines=$(wc -l < "$doc")
        echo "   ✓ $doc ($lines líneas)"
    else
        echo "   ✗ $doc (NO ENCONTRADO)"
    fi
done

echo ""
echo "======================================================"
echo "   VERIFICACIÓN COMPLETADA"
echo "======================================================"

