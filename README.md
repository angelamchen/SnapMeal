<p align="center">
   <img alt="SnapMeal Logo" src="./logo.ico" width="100" />
</p>

# 👩‍🍳 SnapMeal  🍽
SnapMeal will help you determine recipes you can make based off an image of your fridge. With over 10,000 unique recipes, 
SnapMeal uses computer vision to recognize the groceries you currently own to find the best match you can currently make.

Inspired by my real-life problem since I never know what to make for dinner 😛

## 🛠 How it Works 
Each user has their individual list of current ingredients. From the [React frontend](https://github.com/angelamchen/SnapMeal-Frontend), 
the user can add to their list by manually creating ingredients or capturing an image of their fridge. The application then uses computer vision to
add the ingredients identified if the match is above 95%. This generated list of ingredients is used to determine possible recipes the user can cook.

🖌 View the high-level design [here](https://github.com/angelamchen/SnapMeal/blob/master/snapmeal_design_diagram.pdf)!   

## 💻 Tech Stack
Backend: [Spring Boot](https://spring.io/projects/spring-boot)\
Frontend: [React](https://reactjs.org/) and bootstraped using [create-react-app](https://reactjs.org/docs/create-a-new-react-app.html)\
Database: [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)\
Computer Vision: [Clarifai Food Model](https://www.clarifai.com/models/food)

Recipes taken from [Epicurious](https://www.kaggle.com/hugodarwood/epirecipes) Kaggle dataset

Developed in Java and JavaScript\
Previously deployed using AWS Lightsail


## 👩‍🎓Challenges and What I learned

Designing, developing, and testing this project was fun, however I ran into many different challenges. If I were to re-implement this project again,
I would make a couple of changes.

1. I chose to use Spring Boot due to my familiarity with it. However, at the time, MongoDB queries were slightly difficult with Java. Being a more uncommon 
combination, the online documentation was also limited. As a result, it was difficult learning and creating desired queries and the integration was not as smooth
as I would have liked. Since I used React as well for the frontend, next time, I would choose a JavaScript framework (such as Express.js) that has more
documentation. 

2. This was my first time working with large datasets from Kaggle. The dataset I chose had more than 30,000 recipes, many of which had incomplete rows. 
This lead to many null value errors when developing. Specifically, I queried for the ingredients used in each recipe using the "description" field.
However, many of the objects had a null value for this field. Due to my unfamiliarity with the data, I initially was not aware of this, thus resulting in hours 
of head scratching debugging. Afterwards, I found out I needed to clean the data first. As a result, I cleaned the data using a couple of filters:
   * Its description, sodium level, fat, and protein cannot be null
   * It must have a rating of above 4 (we want to be eating good!)
   
3. Due to restrictions from the data set, the only place were the ingredients are listed is in the description, in a paragraph form. Thus, the field is not
queryable. As a result I used `regex` queries, which consumes a lot of CPU time and are extremely slow. For an improvement, I would first extract all the 
ingredients from the `description` field and create a new field called ingredients.

*Overall Thoughts* 
One large issue was the computer vision. If I sent only an image of an apple, the API was able to identify the produce immediately. However, sending an image
of an entire fridge where some items were in special containers, in bulk, or slightly hidden caused the model a lot of trouble. Perhaps training a new model
specifally for foods in fridges would result in better results, and is something I consider learning. Overall, I learned a lot about developing a full stack
application and had a really great time discovering new possibilites from all the different technologies I used!

