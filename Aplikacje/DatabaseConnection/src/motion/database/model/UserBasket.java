package motion.database.model;


@SuppressWarnings("serial")
public class UserBasket extends GenericDescription<UserBasketStaticAttributes>{

	public UserBasket() {
		super(UserBasketStaticAttributes.basketName.name(), EntityKind.userBasket);
	}
	
	public String toString() {
		
		return super.get(UserBasketStaticAttributes.basketName.toString()).value.toString();
	}
}