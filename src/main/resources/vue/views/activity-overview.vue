  <template id="activity-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Activities
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
    <div class="card-body" :class="{ 'd-none': hideForm}">
      <form id="addActivity" @submit.prevent="addActivity">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-activity-name">Description</span>
          </div>
          <input type="text" class="form-control" v-model="formData.description" name="description" placeholder="Which activity you have to do" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-activity-duration">Duration (minutes)</span>
          </div>
          <input type="number" step="0.1" class="form-control" v-model="formData.duration" name="duration" placeholder="How long you have to do" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-activity-calories">Calories</span>
          </div>
          <input type="number" class="form-control" v-model="formData.calories" name="calories" placeholder="calories" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-activity-started">Started</span>
          </div>
          <input type="datetime-local" class="form-control" v-model="formData.started" name="started" required/>
        </div>
        <button type="submit" class="btn btn-primary">Add Activity</button>
      </form>
    </div>
    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="(activity, index) in activities" :key="activity.id">
        <div class="mr-auto p-2">
          <span>
            <a :href="`/activities/${activity.id}`">
              {{ activity.description }}, Calories: {{ activity.calories }},
              Started: {{ formatDateTime(activity.started) }} ({{ activity.duration }} minutes)
            </a>
          </span>
        </div>
        <div class="p2">
          <a :href="`/activities/${activity.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
          </a>
          <button rel="tooltip" title="Delete" class="btn btn-danger btn-simple btn-link"
                  @click="deleteActivity(activity, index)">
            <i class="fas fa-trash" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("activity-overview", {
  template: "#activity-overview",
  data: () => ({
    activities: [],
    formData: {
      description: '',
      duration: null,
      calories: null,
      started: '',
    },
    hideForm: true,
  }),
  created() {
    this.fetchActivities();
  },
  methods: {
    fetchActivities: function () {
      axios.get("/api/activities")
          .then(res => this.activities = res.data)
          .catch(() => alert("Error while fetching activities"));
    },
    deleteActivity: function (activity, index) {
      if (confirm('Are you sure you want to delete this activity? This action cannot be undone.', 'Warning')) {
        const activityId = activity.id;
        const url = `/api/activities/${activityId}`;
        axios.delete(url)
            .then(() => {
              this.activities.splice(index, 1);
              alert('Activity deleted successfully');
            })
            .catch(error => {
              console.error("Error deleting activity:", error);
              alert("Failed to delete activity. Please try again.");
            });
      }
    },
    addActivity: function () {
      const url = `/api/activities`;
      axios.post(url, {
        description: this.formData.description,
        calories: parseInt(this.formData.calories),
        started: this.formData.started,
        duration: parseFloat(this.formData.duration),
        userId: 1  // You might want to get this dynamically
      })
          .then(response => {
            this.activities.push(response.data);
            this.hideForm = true;
            this.formData = {
              description: '',
              duration: null,
              calories: null,
              started: '',
            };
            alert('Activity added successfully');
          })
          .catch(error => {
            console.error("Error adding activity:", error);
            alert("Failed to add activity. Please try again.");
          });
    },
    formatDateTime: function (dateTime) {
      return new Date(dateTime).toLocaleString();
    }
  }
});
</script>
