<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="home-page">
  <app-layout>
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">Registered Users</h5>
          <div class="card-body">
            <h5 class="card-title">{{users.length}} users</h5>
            <a href="/users" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Total Activities</h5>
          <div class="card-body">
            <h5 class="card-title">{{activities.length}} activities</h5>
            <a href="/activities" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card">
        <h5 class="card-header">Total Sleeping</h5>
        <div class="card-body">
          <h5 class="card-title">{{sleeping.length}} sleeping</h5>
          <a href="/sleep" class="btn btn-primary">More Details...</a>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card">
        <h5 class="card-header">Total Diet</h5>
        <div class="card-body">
          <h5 class="card-title">{{diet.length}} diet</h5>
          <a href="/nutrition" class="btn btn-primary">More Details...</a>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card">
        <h5 class="card-header">Total Workouts</h5>
        <div class="card-body">
          <h5 class="card-title">{{workouts.length}} workouts</h5>
          <a href="/workout" class="btn btn-primary">More Details...</a>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component('home-page',
    {
      template: "#home-page",
      data: () => ({
        users: [],
        activities: [],
        sleeping: [],
        diet:[],
        workouts: [],
      }),
      created() {
        axios.get("/api/users")
            .then(res => this.users = res.data)
            .catch(() => alert("Error while fetching users"));
        axios.get("/api/activities")
            .then(res => this.activities = res.data)
            .catch(() => alert("Error while fetching activities"));
        axios.get("/api/sleep")
            .then(res => this.sleeping = res.data)
            .catch(() => alert("Error while fetching sleeping"));
        axios.get("/api/nutrition")
            .then(res => this.diet = res.data)
            .catch(() => alert("Error while fetching nutrition"));
        axios.get("/api/workout")
            .then(res => this.workouts = res.data)
            .catch(() => alert("Error while fetching workout"));
      }
    });
</script>