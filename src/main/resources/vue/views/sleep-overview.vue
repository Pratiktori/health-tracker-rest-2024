<template id="sleep-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Sleeping
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
      <form id="addSleep">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-sleep-name">Name</span>
          </div>
          <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Do you want to sleep"/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-sleep-duration">Duration</span>
          </div>
          <input type="duration" class="form-control" v-model="formData.duration" name="duration" placeholder="How long you have to sleep"/>
        </div>
      </form>
      <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addSleep()">Add Sleep</button>
    </div>
    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="(sleep,index) in sleeping" v-bind:key="index">
        <div class="mr-auto p-2">
          <span><a :href="`/sleep/${sleep.id}`"> {{ sleep.name }} ({{ sleep.started }})</a></span>
        </div>
        <div class="p2">
          <a :href="`/sleep/${sleep.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteSleep(sleep, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </a>
        </div>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("sleep-overview", {
  template: "#sleep-overview",
  data: () => ({
    sleeping: [],
    formData: [],
    hideForm :true,
  }),
  created() {
    this.fetchSleeping();
  },
  methods: {
    fetchSleeping: function () {
      axios.get("/api/sleep")
          .then(res => this.sleeping = res.data)
          .catch(() => alert("Error while fetching sleeping"));
    },
    deleteSleep: function (sleep, index) {
      if (confirm('Are you sure you want to delete this sleep? This action cannot be undone.', 'Warning')) {
        //sleep confirmed delete
        const userId = sleep.id;
        const url = `/api/sleep/${userId}`;
        axios.delete(url)
            .then(response =>
                //delete from the local state so Vue will reload list automatically
                this.sleeping.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addSleep: function (){
      const url = `/api/sleep`;
      axios.post(url,
          {
            name: this.formData.name,
            duration: this.formData.duration
          })
          .then(response => {
            this.sleeping.push(response.data)
            this.hideForm= true;
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>