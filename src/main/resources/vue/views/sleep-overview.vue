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
                    @click="hideForm = !hideForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="card-body" :class="{ 'd-none': hideForm }">
      <form id="addSleep" @submit.prevent="addSleep">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-sleep-name">Name</span>
          </div>
          <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Sleep description" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-sleep-duration">Duration (hours)</span>
          </div>
          <input type="number" step="0.1" class="form-control" v-model="formData.duration" name="duration" placeholder="How long you slept" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-sleep-started">Started</span>
          </div>
          <input type="datetime-local" class="form-control" v-model="formData.started" name="started" required/>
        </div>
        <button type="submit" class="btn btn-primary">Add Sleep</button>
      </form>
    </div>
    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="(sleep, index) in sleeping" :key="sleep.id">
        <div class="mr-auto p-2">
          <span>
            <a :href="`/sleep/${sleep.id}`">
              {{ sleep.name }}, Duration: {{ sleep.duration }} hours,
              Started: {{ formatDateTime(sleep.started) }}
            </a>
          </span>
        </div>
        <div class="p2">
          <a :href="`/sleep/${sleep.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
          </a>
          <button rel="tooltip" title="Delete" class="btn btn-danger btn-simple btn-link"
                  @click="deleteSleep(sleep, index)">
            <i class="fas fa-trash" aria-hidden="true"></i>
          </button>
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
    formData: {
      name: '',
      duration: null,
      started: '',
    },
    hideForm: true,
  }),
  created() {
    this.fetchSleeping();
  },
  methods: {
    fetchSleeping: function () {
      axios.get("/api/sleep")
          .then(res => this.sleeping = res.data)
          .catch(() => alert("Error while fetching sleeping records"));
    },
    deleteSleep: function (sleep, index) {
      if (confirm('Are you sure you want to delete this sleep record? This action cannot be undone.', 'Warning')) {
        const sleepId = sleep.id;
        const url = `/api/sleep/${sleepId}`;
        axios.delete(url)
            .then(() => {
              this.sleeping.splice(index, 1);
              alert('Sleep record deleted successfully');
            })
            .catch(error => {
              console.error("Error deleting sleep record:", error);
              alert("Failed to delete sleep record. Please try again.");
            });
      }
    },
    addSleep: function () {
      const url = `/api/sleep`;
      axios.post(url, {
        name: this.formData.name,
        duration: parseFloat(this.formData.duration),
        started: this.formData.started,
        userId: 1  // You might want to get this dynamically
      })
          .then(response => {
            this.sleeping.push(response.data);
            this.hideForm = true;
            this.formData = {
              name: '',
              duration: null,
              started: '',
            };
            alert('Sleep record added successfully');
          })
          .catch(error => {
            console.error("Error adding sleep record:", error);
            alert("Failed to add sleep record. Please try again.");
          });
    },
    formatDateTime: function (dateTime) {
      return new Date(dateTime).toLocaleString();
    }
  }
});
</script>
