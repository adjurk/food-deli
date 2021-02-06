# Food Deli app

- Model
	- Customer
	- Restaurant
	- DeliveryPerson
	- Food
	- Order
- Controller
	- CustomerController
	- RestaurantController
	- DeliveryPersonController

## Ideas

- Calculate route before ordering to figure if it isn't too far with Bing Maps:
	- Example Query: http://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0=Jakuba%20Wejhera%203A%20Gdańsk&wp.1=Juliana%20Tuwima%2025&key=At36Iu_aivc6Tr6unfWtNd1851vsa2wQHjxNKyTQoY1ivG3u3a8JzZOKq4SfgOR6
		- Compare on: https://www.bing.com/maps
	- Docs: https://docs.microsoft.com/en-us/bingmaps/rest-services/routes/calculate-a-route?redirectedfrom=MSDN#response
	- API key portal: https://www.bingmapsporsitutal.com/Application#
- Food preparation time estimation
	- Based on the longest time to prepare food, show the largest time
	
		```sql
		[dbo.food_prepare_time]

		id | food_id | took_time_to_prepare |
		------------------------------------|
		1  |  23     | 32                   |
		2  |  19     | 12                   |
		3  |  23     | 28                   |
		4  |  19     | 15                   |

		SELECT MAX(AVG(took_time_to_prepare)) FROM food_prepare_time WHERE food_id IN (23,19);

		|------------------------------|
		|MAX(AVG(took_time_to_prepare))|
		|------------------------------|
		| 30                           |
		|------------------------------|
		```

- Enum with order status
- Timestamp when order is created/updated

### Call

- [x] Handle exceptions with custom exceptions (GlobalExceptionHandler)
- [x] Order shouldn't be placed when customer and restaurant does not exist
- [x] Add Postman Collections to add restaurant+customer

## Questions

- H2 Database Float formatting on totalCost? 
- Showing message with 400 response? Restaurant:31

## Cool features

- [x] Run app with Docker
- [ ] API Docs
- [x] Lombok for Mutators/Accessors