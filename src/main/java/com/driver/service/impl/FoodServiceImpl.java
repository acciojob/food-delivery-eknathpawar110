package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FoodServiceImpl implements FoodService {
    // dependancy injection
    @Autowired
    FoodRepository RepoFood;

    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity=FoodEntity.builder().foodCategory(food.getFoodCategory()).foodId(food.getFoodId()).foodName(food.getFoodName()).foodPrice(food.getFoodPrice()).build();
        RepoFood.save(foodEntity);
        return FoodDto.builder().foodId(foodEntity.getFoodId()).foodCategory(foodEntity.getFoodCategory()).foodName(foodEntity.getFoodName()).foodPrice(foodEntity.getFoodPrice()).id(foodEntity.getId()).build();
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity= RepoFood.findByFoodId(foodId);
        if(foodEntity==null){
            throw new Exception("Foodentity Not Exist");
        }
        return FoodDto.builder().foodId(foodEntity.getFoodId()).foodPrice(foodEntity.getFoodPrice()).foodName(foodEntity.getFoodName()).foodCategory(foodEntity.getFoodCategory()).id(foodEntity.getId()).build();
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity= RepoFood.findByFoodId(foodId);
        foodEntity.setFoodId(foodDetails.getFoodId());
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        foodEntity.setId(foodDetails.getId());
        foodEntity= RepoFood.save(foodEntity);
        return FoodDto.builder().foodId(foodEntity.getFoodId()).foodPrice(foodEntity.getFoodPrice()).foodName(foodEntity.getFoodName()).foodCategory(foodEntity.getFoodCategory()).id(foodEntity.getId()).build();
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        RepoFood.delete(RepoFood.findByFoodId(id));
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity> foodEntities= (List<FoodEntity>) RepoFood.findAll();
        List<FoodDto> foodDtos=null;
        for(FoodEntity foodEntity:foodEntities){
            foodDtos.add(FoodDto.builder().foodName(foodEntity.getFoodName()).foodPrice(foodEntity.getFoodPrice()).foodId(foodEntity.getFoodId()).foodCategory(foodEntity.getFoodCategory()).id(foodEntity.getId()).build());
        }
        return foodDtos;
    }
}