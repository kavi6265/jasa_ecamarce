package com.example.demo;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addProductToCart(Long userId, Long productId, Integer quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());

        cartItemRepository.save(item);

        return cart;
    }
    public List<CartItem> getCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCartId(cart.getId());
    }
    public CartItem updateQuantity(Long cartItemId, Integer quantity) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }
    public String removeCartItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(item);

        return "Item removed from cart";
    }
    public BigDecimal getCartTotal(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {
            total = total.add(
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        return total;
    }
}