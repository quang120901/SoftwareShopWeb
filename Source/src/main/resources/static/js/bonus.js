
    function incrementQuantity() {
        const quantityInput1 = document.getElementById('product-quantity');
        let currentValue = parseInt(quantityInput1.value, 10);
        const quantityInput2 = document.getElementById('quantity');

        quantityInput1.value = currentValue + 1;
        quantityInput2.value = currentValue + 1;
    }

    function decrementQuantity() {
        const quantityInput1 = document.getElementById('product-quantity');
        let currentValue = parseInt(quantityInput1.value, 10);
        const quantityInput2 = document.getElementById('quantity');
        if (currentValue > 1) { // Ensure value doesn't go below 1
            quantityInput1.value = currentValue - 1;
            quantityInput2.value = currentValue - 1;
        }
    }
