<template id="workout-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Workouts
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
      <form id="addWorkout">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-workout-name">Name</span>
          </div>
          <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Name"/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-workout-performedAt">Performed</span>
          </div>
          <input type="performedAt" class="form-control" v-model="formData.performedAt" name="performedAt" placeholder="What time you performed"/>
        </div>
      </form>
      <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addWorkout()">Add Workout</button>
    </div>


    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="(workout,index) in workouts" v-bind:key="index">
        <div class="mr-auto p-2">
          <span><a :href="`/workout/${workout.id}`"> {{ workout.name }} ({{ workout.performedAt }})</a></span>
        </div>
        <div class="p2">
          <a :href="`/workout/${workout.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteWorkout(workout, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </a>
        </div>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("workout-overview", {
  template: "#workout-overview",
  data: () => ({
    workouts: [],
    formData: [],
    hideForm :true,
  }),
  created() {
    this.fetchWorkouts();
  },
  methods: {
    fetchWorkouts: function () {
      axios.get("/api/workout")
          .then(res => this.workouts = res.data)
          .catch(() => alert("Error while fetching workouts"));
    },
    deleteWorkout: function (workout, index) {
      if (confirm('Are you sure you want to delete this workout? This action cannot be undone.', 'Warning')) {
        //workout confirmed delete
        const userId = workout.id;
        const url = `/api/workout/${userId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.workouts.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addWorkout: function (){
      const url = `/api/workout`;
      axios.post(url,
          {
            name: this.formData.name,
            performedAt: this.formData.performedAt
          })
          .then(response => {
            this.workouts.push(response.data)
            this.hideForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>