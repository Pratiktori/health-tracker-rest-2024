<template id="nutrition-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Nutrition List
          </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm =!hideForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="card-body" :class="{ 'd-none': hideForm}">
      <form id="addNutrition">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-nutrition-name">FoodName</span>
          </div>
          <input type="text" class="form-control" v-model="formData.foodname" name="foodname" placeholder="What would you like to have"/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-nutrition-consumedAt">Consumed</span>
          </div>
          <input type="consumedAt" class="form-control" v-model="formData.consumedAt" name="consumedAt" placeholder="Consumed at "/>
        </div>
      </form>
      <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addNutrition()">Add Nutrition</button>
    </div>
    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="(nutrition,index) in diet" v-bind:key="index">
        <div class="mr-auto p-2">
          <span><a :href="`/nutrition/${nutrition.id}`"> {{ nutrition.foodName }} ({{ nutrition.consumedAt }})</a></span>
        </div>
        <div class="p2">
          <a :href="`/nutrition/${nutrition.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteNutrition(nutrition, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </a>
        </div>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("nutrition-overview", {
  template: "#nutrition-overview",
  data: () => ({
    diet: [],
    formData: [],
    hideForm :true,
  }),
  created() {
    this.fetchDiet();
  },
  methods: {
    fetchDiet: function () {
      axios.get("/api/nutrition")
          .then(res => this.diet = res.data)
          .catch(() => alert("Error while fetching diet"));
    },
    deleteNutrition: function (nutrition, index) {
      if (confirm('Are you sure you want to delete this nutrition? This action cannot be undone.', 'Warning')) {
        //nutrition confirmed delete
        const userId = nutrition.id;
        const url = `/api/nutrition/${userId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.diet.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addNutrition: function (){
      const url = `/api/nutrition`;
      axios.post(url,
          {
            foodname: this.formData.foodname,
            consumedAt: this.formData.consumedAt
          })
          .then(response => {
            this.diet.push(response.data)
            this.hideForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>